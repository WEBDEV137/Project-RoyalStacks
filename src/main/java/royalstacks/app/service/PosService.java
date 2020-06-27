package royalstacks.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import royalstacks.app.model.Account;
import royalstacks.app.model.Transaction;
import royalstacks.app.model.pos.JournalEntry;
import royalstacks.app.model.pos.PaymentData;
import royalstacks.app.model.pos.PaymentResult;
import royalstacks.app.model.pos.Pos;
import royalstacks.app.model.repository.JournalEntryRepository;
import royalstacks.app.model.repository.PosRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PosService {

    PosRepository posRepository;
    TransactionService transactionService;
    AccountService accountService;
    JournalEntryRepository journalEntryRepository;

    @Autowired
    public PosService(PosRepository pr, TransactionService ts, AccountService as, JournalEntryRepository jer){
        this.posRepository = pr;
        this.transactionService = ts;
        this.accountService = as;
        this.journalEntryRepository = jer;
    }

    public PaymentResult executePosTransaction(Pos pos,  PaymentData pd){
        Transaction t = new Transaction();
        PaymentResult pr = new PaymentResult();

        if (!isPaymentValid(pos, pd, t, pr)){ return pr; }

        t.setAmount(pd.getAmount());
        t.setDescription("POSid: " + pd.getIdentificationNumber());
        t.setDate(LocalDateTime.now());
        Optional<Transaction> transOpt = transactionService.executeTransaction(t);

        if(transOpt.isPresent()){
            pr.setDate(transOpt.get().getDate());
            pr.setTransactionId(transOpt.get().getTransactionId());
            pr.setPaymentSuccess(true);
        }
        return pr;
    }

    private boolean isPaymentValid(Pos pos, PaymentData pd, Transaction t, PaymentResult pr) {
        Optional<Integer> fromAccountIdOpt = accountService.getAccountIdByNumberExIban(pd.getAccount());
        Optional<Integer> toAccountIdOpt = accountService.getAccountIdByAccountNumber(pos.getBusinessAccountNumber());

        if(fromAccountIdOpt.isEmpty() || toAccountIdOpt.isEmpty()){
            return false;
        } else {
            if (fromAccountIdOpt.get().equals(toAccountIdOpt.get())) {
                return false;
            } else {
                t.setToAccountId(toAccountIdOpt.get());
                t.setFromAccountId(fromAccountIdOpt.get());
                pr.setAccountVerified(true);
            }
        }

        if(accountService.getAccountById(t.getFromAccountId()).getBalance().compareTo(pd.getAmount()) < 0){
            return false;
        } else {
            pr.setSufficientBalance(true);

        }
        return true;
    }

    public void saveJournalEntry(PaymentData pd, PaymentResult pr, Pos pos) {
        JournalEntry je = new JournalEntry();
        je.setDate(pr.getDate());
        je.setTransactionId(pr.getTransactionId());
        je.setAccountNumber(pd.getAccount());
        je.setAmount(pd.getAmount());
        pos.addJournalEntry(je);
        journalEntryRepository.save(je);
    }

    public Optional<Pos> findPosByIdentificationNumber(int identificationNumber){
        return posRepository.findPosByIdentificationNumber(identificationNumber);
    }

    public void savePos(Pos pos){
        posRepository.save(pos);
    }

    public Optional<Integer> getLastPosId(){
        return posRepository.getLastId();
    }
}
