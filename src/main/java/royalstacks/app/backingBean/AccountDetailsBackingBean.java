package royalstacks.app.backingBean;

import royalstacks.app.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AccountDetailsBackingBean {

    private int accountId;
    private String accountType;
    private String accountNumber;
    private BigDecimal balance;
    private List<CustomerDetails> accountHolders;

    public AccountDetailsBackingBean(String accountNumber, BigDecimal balance) {
        this(null,accountNumber,balance);


    }


    public AccountDetailsBackingBean(String accountType, String accountNumber, BigDecimal balance) {

        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }


    public static AccountDetailsBackingBean createBeanPrivate(PrivateAccount account){
        AccountDetailsBackingBean bb =
                new AccountDetailsBackingBean(account.getAccountNumber(),account.getBalance());
        bb.setAccountType("Private Account");
        bb.setAccountId(account.getAccountId());
        bb.setAccountHolders( getAccountHoldersFirstAndLastName(account));
        return bb;

    }

    private static List<CustomerDetails> getAccountHoldersFirstAndLastName(Account account) {
        Set<Customer> accountholders =  account.getAccountHolders();
        List<CustomerDetails> customerAndAccountDetails = new ArrayList<>();
        for(Customer customer: accountholders){
            CustomerDetails customerDetails = new CustomerDetails();
            customerDetails.setFirstName(customer.getFirstName());
            customerDetails.setLastName(customer.getLastName());
            customerAndAccountDetails.add(customerDetails);
        }

        return customerAndAccountDetails;

    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }


    public static AccountDetailsBackingBean createBeanBusiness(BusinessAccount account){
        AccountDetailsBackingBean bb =
                new AccountDetailsBackingBean(account.getAccountNumber(),account.getBalance());
        bb.setAccountType("Business Account");
        bb.setAccountId(account.getAccountId());
        bb.setAccountHolders(getAccountHoldersFirstAndLastName(account));
        return bb;

    }




    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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


    public List<CustomerDetails> getAccountHolders() {
        return accountHolders;
    }

    public void setAccountHolders(List<CustomerDetails> accountHolders) {
        this.accountHolders = accountHolders;
    }

    @Override
    public String toString() {
        return "AccountDetailsBackingBean{" +
                ", accountType='" + accountType + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }


}
