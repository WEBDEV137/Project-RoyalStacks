package royalstacks.app.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account implements Comparable<Account>{

    protected final static double STARTING_BALANCE = 1337.00;

    @Id
    @GeneratedValue
    protected int accountId;
    protected String accountNumber;
    protected BigDecimal balance;
    @ManyToMany (cascade = CascadeType.ALL)
    protected Set<Customer> accountHolders;

    // CONSTRUCTORS

    public Account(String accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        accountHolders = new HashSet<>();
    }

    // generates new account
    public Account() {
        this("", BigDecimal.valueOf(STARTING_BALANCE));
    }

    //METHODS
    public boolean hasSufficientBalance(BigDecimal sentAmount){
        return this.balance.compareTo(sentAmount) >= 0;
    }

    public void subtractAmount(BigDecimal subtractAmount){
        this.setBalance(this.getBalance().subtract(subtractAmount));
    }

    public void addAmount(BigDecimal addAmount){
        this.setBalance(this.getBalance().add(addAmount));
    }

    public void addAccountHolder(Customer accountHolderToAdd){
            accountHolders.add(accountHolderToAdd);
    }

    //Getters and Setters
    public static BigDecimal getStartingBalance() {
        return BigDecimal.valueOf(STARTING_BALANCE);
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Set<Customer> getAccountHolders() {
        return accountHolders;
    }

    public void setAccountHolders(Set<Customer> accountHolders) {
        this.accountHolders = accountHolders;
    }

    @Override
    public int compareTo(Account otherAccount) {
        // sort by last 10 digits (ignore first 8 nrs being: NL / checknum / ROYA )

        return this.accountNumber.substring(8).compareTo(otherAccount.accountNumber.substring(8));
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
