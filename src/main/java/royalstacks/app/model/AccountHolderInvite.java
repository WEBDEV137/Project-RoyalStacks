package royalstacks.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class AccountHolderInvite {

    @Id
    @GeneratedValue
    private int inviteId;
    @ManyToOne
    private Customer invitee;
    @ManyToOne
    private Account account;
    private String verificationCode;

    //CONSTRUCTORS
    //all args

    public AccountHolderInvite(Customer invitee, Account account, String verificationCode) {
        this.invitee = invitee;
        this.account = account;
        this.verificationCode = verificationCode;
    }

    //default
    public AccountHolderInvite() {}

    //GETTERS AND SETTERS

    public Customer getInvitee() {
        return invitee;
    }

    public void setInvitee(Customer invitee) {
        this.invitee = invitee;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Override
    public String toString() {
        return "AccountHolderInvite{" +
                "invitee=" + invitee +
                ", account=" + account +
                ", verificationCode='" + verificationCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountHolderInvite that = (AccountHolderInvite) o;
        return Objects.equals(invitee, that.invitee) &&
                Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invitee, account);
    }

}
