package royalstacks.app.backingBean;

import royalstacks.app.model.Account;
import royalstacks.app.model.AccountHolderInvite;
import royalstacks.app.model.Customer;
import royalstacks.app.model.User;

import java.util.Objects;

public class AccountHolderInviteBackingBean {

    private Customer invitee;
    private String inviteeUsername;
    private Account account;
    private String accountNumber;
    private String verificationCode;

    public AccountHolderInviteBackingBean(String inviteeUsername, String accountNumber, String verificationCode) {
        this.inviteeUsername = inviteeUsername;
        this.accountNumber = accountNumber;
        this.verificationCode = verificationCode;
    }


    public AccountHolderInvite accountHolderInvite(Customer invitee, Account account, String verificationCode) {
        AccountHolderInvite accountHolderInvite = new AccountHolderInvite(invitee, account, verificationCode);
        return accountHolderInvite;
    }


    public Customer getInvitee() {
        return invitee;
    }

    public void setInvitee(Customer invitee) {
        this.invitee = invitee;
    }

    public String getInviteeUsername() {
        return inviteeUsername;
    }

    public void setInviteeUsername(String inviteeUsername) {
        this.inviteeUsername = inviteeUsername;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }


    @Override
    public String toString() {
        return "AccountHolderInviteBackingBean{" +
                "inviteeUsername='" + inviteeUsername + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                '}';
    }
}
