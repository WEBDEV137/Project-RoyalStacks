package royalstacks.app.model;

import royalstacks.app.model.Account;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class PrivateAccount extends Account {

    // CONSTRUCTORS
    public PrivateAccount(String accountNumber, BigDecimal balance) {
        super(accountNumber, balance);
    }

    public PrivateAccount() {
    }

    @Override
    public String toString() {
        return "PrivateAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
