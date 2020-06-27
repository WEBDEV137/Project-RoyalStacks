package royalstacks.app.controller;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import royalstacks.app.backingBean.TransactionBackingBean;
import royalstacks.app.model.Account;
import royalstacks.app.model.Customer;
import royalstacks.app.service.AccountService;
import royalstacks.app.service.CustomerService;
import royalstacks.app.service.TransactionService;
import java.util.*;

@Controller
public class TransactionController {

    private static final String INVALID_INPUT = "Transaction failed: invalid input";
    private static final String TRANSACTION_SUCCESS = "Money successfully sent";
    private static final String TRANSACTION_FAILED = "Transaction failed: failed to execute";

    private AccountService accountService;
    private TransactionService transactionService;
    private CustomerService customerService;
    private ModelAndView mav;
    private TransactionBackingBean tbb;

    @Autowired
    public TransactionController(AccountService as, TransactionService ts, CustomerService cs){
        this.accountService = as;
        this.transactionService = ts;
        this.customerService = cs;
    }

    public TransactionController() {
    }

    /**
     * GetMapping
     * Account van de accountId zal als eerst getoond worden als deze meegegeven wordt
     */
    @GetMapping("/transaction")
    public ModelAndView transactionHandler(@RequestParam (required = false) Integer accountId,
                                           @SessionAttribute("userid") int userId,
                                           Model model) {

        this.mav = new ModelAndView("transaction");
        showAccountsOfUserId(model, userId, accountId);
        return this.mav;
    }
    /**
     * PostMapping
     */
    @PostMapping("/transaction")
    public ModelAndView transactionHandler(@ModelAttribute TransactionBackingBean transactionBackingBean,
                                           @RequestParam (required = false) Integer accountId,
                                           @SessionAttribute("userid") int userId,
                                           Model model) {

        showAccountsOfUserId(model, userId, accountId);
        this.mav = new ModelAndView("transaction");
        this.tbb = transactionBackingBean;

        Optional<Account> fromAccountOpt = getAccount(this.tbb.getFromAccountNumber());
        Optional<Account> toAccountOpt = getAccount(this.tbb.getToAccountNumber());

        if (fromAccountOpt.isPresent() && toAccountOpt.isPresent()) {
            setAccountsInBean(fromAccountOpt.get(), toAccountOpt.get());
            executeTransaction();
        } else {
            showNotification(INVALID_INPUT);
            populateFields();
        }
        return this.mav;
    }

    private Optional<Account> getAccount(String accountNumber){
        return accountService.getAccountByIban(accountNumber);
    }

    private void setAccountsInBean(Account fromAccount, Account toAccount){
        this.tbb.setFromAccountId(fromAccount.getAccountId());
        this.tbb.setToAccountId(toAccount.getAccountId());
    }

    private void showNotification(String notification){
        this.mav.addObject("notification", notification);
    }

    private void populateFields() {
        this.mav.addObject("toAccountNumber", this.tbb.getToAccountNumber());
        this.mav.addObject("amount", this.tbb.getAmount());
        this.mav.addObject("description", this.tbb.getDescription());
    }

    private void executeTransaction() {
        if (transactionService.executeTransaction(this.tbb.transaction()).isPresent()) {
            showNotification(TRANSACTION_SUCCESS);
        } else {
            showNotification(TRANSACTION_FAILED);
            populateFields();
        }
    }

    /**
     * Haalt alle Accounts van userId op
     * Als accountId meegegeven wordt wordt de bijbehorende Account bovenaan gezet
     */
    // checked by tom
    @SuppressWarnings("unchecked")
    private void showAccountsOfUserId(Model model, int userId, Integer accountId) {
        Optional<Customer> customerOptional = customerService.findCustomerByUserId(userId);

        if(customerOptional.isPresent()) {
            Customer currentUser = customerOptional.get();
            List<Account> myAccounts = IteratorUtils.toList(currentUser.getAccount().iterator());

            // accountId wordt meegegeven als de gebruiker vanuit AccountDetails komt
            if (accountId != null) {
                Account account = accountService.getAccountById(accountId);

                // check of account daadwerkelijk bestaat en van de currentUser is
                if (account != null && account.getAccountHolders().contains(currentUser)) {

                    myAccounts.remove(account);
                    myAccounts.add(0, account);
                }
            }
            model.addAttribute("account", myAccounts);
        }
    }
}