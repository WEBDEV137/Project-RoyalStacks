package royalstacks.app.controller.pos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import royalstacks.app.model.pos.*;
import royalstacks.app.model.repository.JournalEntryRepository;
import royalstacks.app.service.AccountService;
import royalstacks.app.service.ConnectionRequestService;
import royalstacks.app.service.ConnectionResultService;
import royalstacks.app.service.PosService;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class PosController {

    PosService posService;
    AccountService accountService;
    ConnectionResultService connectionResultService;
    ConnectionRequestService connectionRequestService;
    JournalEntryRepository journalEntryRepository;

    @Autowired
    public PosController(PosService ps, ConnectionResultService cRes, ConnectionRequestService conReq,
                         AccountService as, JournalEntryRepository je) {
        this.posService = ps;
        this.connectionResultService = cRes;
        this.connectionRequestService = conReq;
        this.accountService = as;
        this.journalEntryRepository = je;
    }


    @GetMapping("/pos")
    public final ModelAndView posCustomerTestControllerHandler(Model model) {
        Pos dummy = new Pos();
        model.addAttribute(dummy);
        return new ModelAndView("pos");
    }


    @GetMapping("/pos/{identificationNumber}")
    public final ModelAndView posControllerHandler(@PathVariable("identificationNumber") int identificationNumber, Model model) {
        Pos pos = new Pos();
        Optional<Pos> posOptional = posService.findPosByIdentificationNumber(identificationNumber);

        if (posOptional.isPresent()) {
            pos = posOptional.get();
        }

        model.addAttribute("pos", pos);
        return new ModelAndView("pos");
    }

    // curl -H "Content-Type: application/json" -X POST -d '{"id":"0", "businessAccountNumber":"NL32ROYA0000000019", "identificationNumber":"1232", "pendingAmount":"100", "clientAccountNumber":"1111111111"}' http://localhost/pos/client/
    @PostMapping("/pos/client")
    public @ResponseBody ResponseEntity<PaymentResult> StartPosClient(@RequestBody PaymentData pd) {

        Optional<Pos> posOpt = posService.findPosByIdentificationNumber(Integer.parseInt(pd.getIdentificationNumber()));
        PaymentResult pr = new PaymentResult();

        if(posOpt.isPresent()){
            Pos pos = posOpt.get();
            pr = posService.executePosTransaction(pos, pd);
            if(pr.isPaymentSuccess()) {
                pr.setDate(LocalDateTime.now());
                posService.saveJournalEntry(pd, pr, pos);
            }
        }
        return new ResponseEntity<>(pr, HttpStatus.OK);
    }


    @PostMapping("/pos/connect")
    public @ResponseBody ConnectionResult posConnectionResult(@RequestBody ConnectionRequest connectionRequest){

        ConnectionResult returnValue = connectionResultService.checkConnectionResult(connectionRequest);

        if(returnValue.isSucceeded()){
            ConnectionRequest cr = connectionRequestService.findCustomerRequestByBusinessAccountIban(connectionRequest.getBusinessAccountNumber()).get();

            Pos pos = new Pos();
            pos.setIdentificationNumber((int) returnValue.getId());
            pos.setBusinessAccountNumber(cr.getBusinessAccountNumber());
            posService.savePos(pos);

            connectionRequestService.delete(cr);
        }
        return returnValue;
    }
}
