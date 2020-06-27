package royalstacks.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import royalstacks.app.backingBean.AccountHolderInviteBackingBean;
import royalstacks.app.model.*;
import royalstacks.app.model.repository.AccountHolderInviteRepository;
import royalstacks.app.model.repository.EmployeeRepository;
import royalstacks.app.service.AccountHolderInviteService;
import royalstacks.app.service.AccountService;
import royalstacks.app.service.BusinessAccountService;
import royalstacks.app.service.UserService;

import java.util.Optional;

@Controller
public class AcceptAccountHolderInviteController {

    @Autowired
    AccountService accountService;

    @Autowired
    BusinessAccountService businessAccountService;

    @Autowired
    UserService userService;

    @Autowired
    AccountHolderInviteService accountHolderInviteService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AccountHolderInviteRepository accountHolderInviteRepository;


    @GetMapping("/acceptaccountholderinvite")
    public ModelAndView acceptAccountHolderInviteHandler(@SessionAttribute("userid") int userId) {
        return new ModelAndView("acceptaccountholderinvite");
    }


    // deze methode is enorm, nog opschonen en uit elkaar trekken
    @PostMapping("/acceptaccountholderinvite")
    public ModelAndView acceptAccountHolderInviteHandler(@ModelAttribute AccountHolderInviteBackingBean ibb,
                                                         @SessionAttribute("userid") int userId,
                                                         Model model) {
        ModelAndView mav = new ModelAndView("acceptaccountholderinvite");
        User invitee = null;
        Account accountToBeAdded = null;

        //met meegegeven userId de user ophalen en ifPresent get als invitee
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isPresent()) {
            invitee = optionalUser.get();
        }

        // vanuit de backing bean een optional account ophalen en ifPresent get als account
        Optional<Account> optionalAccount = accountService.getAccountByIban(ibb.getAccountNumber());
        if (optionalAccount.isPresent()) {
            accountToBeAdded = optionalAccount.get();
        } else { //om veiligheidsredenen hier een generieke foutmelding
            displayMessage("The account number and/or verification code you entered is incorrect.", mav);
            populateFields(ibb, mav);
            return mav;
        }
        // invite aanmaken en checken of deze in de DB bestaat (Customer invitee, Account account, String verif.code)
        AccountHolderInvite inviteToBeVerified = new AccountHolderInvite((Customer) invitee, accountToBeAdded, ibb.getVerificationCode());
        Optional<AccountHolderInvite> existingInvite = accountHolderInviteService.findAccountHolderInviteByAccountAndInviteeAndCode(inviteToBeVerified.getInvitee().getUserid(), inviteToBeVerified.getAccount().getAccountId(), inviteToBeVerified.getVerificationCode());
        //ifPresent: voeg customer toe als accountholder en check op businessaccount, zo ja voeg dan toe aan lijst
        if (existingInvite.isPresent()) {
            AccountHolderInvite inviteToBeProcessed = existingInvite.get();
            if (isBusinessAccount(accountToBeAdded)) {
                if (!((Customer) invitee).isBusinessAccountHolder()) {
                    ((Customer) invitee).setBusinessAccountHolder(true);
                }
                if (employeeRepository.findAll().iterator().hasNext()) {
                    ((Customer) invitee).setAccountManager(employeeRepository.findAll().iterator().next());
                }
            }
            accountToBeAdded.addAccountHolder((Customer) invitee);
            accountService.saveAccount(accountToBeAdded);
            accountHolderInviteRepository.delete(inviteToBeProcessed);
            displayMessage("Account added.", mav);
            System.out.println("Account added");
        } else {
            displayMessage("The account number and/or verification code you entered is incorrect.", mav);
            populateFields(ibb, mav);
        }
        return mav;
    }


    private void displayMessage(String message, ModelAndView mav){
        mav.addObject("message", message);
    }


    private void populateFields(AccountHolderInviteBackingBean ibb, ModelAndView mav) {
        mav.addObject("accountNumber",ibb.getAccountNumber());
        mav.addObject("verificationCode", ibb.getVerificationCode());
    }

    // checkt of accountId voorkomt in tabel business_account, hoort eigenlijk niet in deze klasse, nog verplaatsen
    public boolean isBusinessAccount(Account account) {
        Optional<BusinessAccount> optionalBusinessAccount = businessAccountService.findByAccountId(account.getAccountId());
        return (optionalBusinessAccount.isPresent());
    }




}
