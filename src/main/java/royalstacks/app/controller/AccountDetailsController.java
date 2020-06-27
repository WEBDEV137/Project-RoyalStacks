package royalstacks.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import royalstacks.app.backingBean.AccountDetailsBackingBean;
import royalstacks.app.model.AccountHolderTransaction;
import royalstacks.app.model.*;
import royalstacks.app.model.PrivateAccount;
import royalstacks.app.service.AccountService;
import royalstacks.app.service.TransactionService;
import royalstacks.app.service.UserService;

import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.core.annotation.MergedAnnotations.from;

/**
 * Auteur: Timothee Busch
 */
@Controller
public class AccountDetailsController {
    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    public AccountDetailsController() {
    }

    @GetMapping("/accountdetails")
    public ModelAndView accountDetailsHandler( @SessionAttribute("userid") int userId, @RequestParam(value = "accountNumber",required = false) String accountNumber, @RequestParam(value = "fromAccountNumber",required = false) String fromAccountNumber) {

        ModelAndView mav = new ModelAndView("accountdetails");

        List<Account> myAccounts = accountService.getAccountsFromUserId(userId);
        mav.addObject("accounts",myAccounts); //TU USE IN DROPDWON SELECT EVENTUALLY: DO NO FORGET TO ERASE IT IF ITS NOT USED!!!!!!!!!!!
        String accountNumberCookie;
        //check if accountnumber belongs to user:
        if(accountNumber!=null) {
             accountNumberCookie = accountNumber;
        }
        else{
            accountNumberCookie = fromAccountNumber;
        }
        for(Account account: myAccounts){
            if(accountNumberCookie.matches(account.getAccountNumber())){
            Account myAccount = accountService.getAccountFromAccountNumber(accountNumberCookie) /*getAccountFromAccountNumber(accountNumberCookie)*/;
            mav.addObject("account", getAccountdetailsbb(myAccount));
            return mav;
            }
        }
        //else redirect the user to homepage

        return new ModelAndView("homepage");

    }



    //PUT DE ACCOUNT IN ACCOUNT DETAILS BACKING BEAN
    public AccountDetailsBackingBean getAccountdetailsbb(Account myAccount) {
        AccountDetailsBackingBean accountDetailsBackingBean = null;
        if(myAccount instanceof PrivateAccount){
            accountDetailsBackingBean = AccountDetailsBackingBean.createBeanPrivate((PrivateAccount) myAccount);
        }else if (myAccount instanceof BusinessAccount)
            accountDetailsBackingBean = AccountDetailsBackingBean.createBeanBusiness((BusinessAccount) myAccount);
        return accountDetailsBackingBean;
    }



    //METHODE DIE HAALT  ACCOUNTS DIE HOREN BIJ HET GEBRUIKER

    public List<Account> getAccountsFromUserId(int userId) {
        Customer customer = (Customer) userService.findByUserId(userId);

        return new ArrayList<>(customer.getAccount());
    }


    @GetMapping("/accountdetails/accountNumber")
    public@ResponseBody AccountDetailsBackingBean getNewAccountInformation(@RequestParam String accountNumber){
        Account account = accountService.getAccountFromAccountNumber(accountNumber);
        return  getAccountdetailsbb(account) ;
    }



}

