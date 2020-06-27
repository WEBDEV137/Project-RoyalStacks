package royalstacks.app.controller.pos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import royalstacks.app.model.Employee;
import royalstacks.app.model.pos.ConnectionRequest;
import royalstacks.app.service.AccountService;
import royalstacks.app.service.BusinessAccountService;
import royalstacks.app.service.ConnectionRequestService;
import royalstacks.app.service.EmployeeService;

import java.util.Optional;
import java.util.Random;

@RestController
public class GeneratePosConnectionController {

    AccountService accountService;
    ConnectionRequestService connectionRequestService;
    BusinessAccountService businessAccountService;
    EmployeeService employeeService;

    @Autowired
    public GeneratePosConnectionController(AccountService accountService,
                                           ConnectionRequestService connectionRequestService,
                                           BusinessAccountService businessAccountService,
                                           EmployeeService employeeService) {
        this.accountService = accountService;
        this.connectionRequestService = connectionRequestService;
        this.businessAccountService = businessAccountService;
        this.employeeService = employeeService;
    }

    @GetMapping("/pos/employee")
    public final ModelAndView generatePosConnectionHandler(@SessionAttribute("userid") int userId) {
        Optional<Employee> employeeOpt = employeeService.findById(userId);
        if(employeeOpt.isPresent()){
            return new ModelAndView("generateposconnection");
        } else {
            return new ModelAndView("homepage");
        }
    }


    @PostMapping("/pos/employee")
    public final ModelAndView generatePosConnectionPostHandler(@RequestParam String businessAccountIban){
        ModelAndView mav = new ModelAndView("generateposconnection");

        if(businessAccountService.findBusinessAccountByAccountNumber(businessAccountIban).isEmpty()){
            return mav;
        }

        ConnectionRequest connectionRequest = new ConnectionRequest();
        Optional<ConnectionRequest> crOptional = connectionRequestService.findCustomerRequestByBusinessAccountIban(businessAccountIban);

        if (crOptional.isPresent()) {
            connectionRequest = crOptional.get();
        } else {
            connectionRequest.setBusinessAccountNumber(businessAccountIban);
        }


        int connectionCode = 10000 + new Random().nextInt(90000);
        connectionRequest.setConnectionCode(connectionCode);
        connectionRequestService.saveConnectionRequest(connectionRequest);
        mav.addObject("businessAccountIban", businessAccountIban);
        mav.addObject("connectionCode", connectionCode);
        return mav;
    }

}
