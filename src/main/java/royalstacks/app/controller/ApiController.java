package royalstacks.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import royalstacks.app.model.Account;
import royalstacks.app.model.AccountHolderTransaction;
import royalstacks.app.model.BusinessAccount;
import royalstacks.app.model.CompanyAndTransactions;
import royalstacks.app.model.User;
import royalstacks.app.service.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ApiController {


    private AccountService accountService;
    private UserService userService;
    private CustomerService customerService;
    private BusinessAccountService businessAccountService;
    private PosService posService;
    private TransactionService transactionService;

    @Autowired
    public ApiController(AccountService as, UserService us, CustomerService cs,  BusinessAccountService bs, PosService ps, TransactionService ts){
        this.accountService = as;
        this.userService = us;
        this.customerService = cs;
        this.businessAccountService = bs;
        this.posService = ps;
        this.transactionService = ts;
    }

    @GetMapping("/api/accountredirect")
    @ResponseBody
    public void accountredirect(@RequestParam String accountnumber){
        System.out.println("hij draait de account-redirect methode");

    }

    @GetMapping("/api/username")
    @ResponseBody
    public String isUsernameUniqueHandler(@RequestParam String username){
        return String.valueOf(userService.findByUsername(username).isEmpty());
    }

    @GetMapping("/api/usernameExist")
    @ResponseBody
    public String doesUsernameExistHandler(@RequestParam String username){
        return String.valueOf(userService.findByUsername(username).isPresent());
    }


    @GetMapping("/api/doesAccountNumberExist")
    @ResponseBody
    public String doesAccountNumberExistHandler(@RequestParam String accountNumber){
        return String.valueOf(accountService.getAccountByIban(accountNumber).isPresent());
    }


    @GetMapping("/api/bsn")
    @ResponseBody
    public String isBSNUniqueHandler(@RequestParam String bsn) {
        return String.valueOf(customerService.findCustomerByBSN(bsn).isEmpty());

    }

    @GetMapping("/api/isBusinessAccount")
    @ResponseBody
    public String isBusinessAccountHandler(@RequestParam String accountNumber) {
        return String.valueOf(businessAccountService.findBusinessAccountByAccountNumber(accountNumber).isPresent());

    }

    @GetMapping("/openaccount/v_check")
    @ResponseBody
    public String vatNumberCheckHandler(@RequestParam String vatnumber){
        BusinessAccount businessAccount =  new BusinessAccount();
        businessAccount.setVatNumber(vatnumber);
        return String.valueOf(businessAccount.isVatValid());
    }

    @GetMapping("/api/top10transactions")
    public @ResponseBody List<CompanyAndTransactions> topTransactionList(){
        return businessAccountService.findTop10TransactionsOnBusinessAccounts();
    }


    @PostMapping(value = "/api/iban",consumes = "text/plain")
    @ResponseBody
    public String ibanCheckHandler(@RequestBody String iban) throws Exception{
        return String.valueOf(accountService.getAccountByIban(iban).isPresent());
    }

    @GetMapping("/api/transactions")
    public@ResponseBody List<AccountHolderTransaction> LastTenTransactionList(@RequestParam String accountNumber){
        Account account = accountService.getAccountFromAccountNumber(accountNumber);
        return transactionService.getTenLastTransaction(account.getAccountId());
    }
}
