package royalstacks.app.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Customer extends User {

    private String email;
    private String phoneNumber;
    private String BSN;
    @OneToOne(cascade = CascadeType.ALL)
    private CustomerAddress customerAddress;
    private boolean isBusinessAccountHolder;
    @ManyToOne(cascade = CascadeType.ALL)
    private Employee accountManager;
    @ManyToMany(mappedBy = "accountHolders")
    // TODO add mapping after implementing config file hibernate
    private Set<Account> account;

    // CONSTRUCTORS
    // all args
    public Customer(int userid, String username, String password, String firstName, String lastName, String email, CustomerAddress customerAddress, String phoneNumber, String BSN, Employee accountManager, boolean isBusinessAccountHolder) {
        super(userid, username, password, firstName, lastName);
        this.email = email;
        this.customerAddress = customerAddress;
        this.phoneNumber = phoneNumber;
        this.BSN = BSN;
        this.accountManager = accountManager;
        this.isBusinessAccountHolder = isBusinessAccountHolder;
    }

    // om customer op te slaan in DB
    public Customer(String username, String password, String firstName, String lastName, String email, CustomerAddress customerAddress, String phoneNumber, String BSN, Employee accountManager, boolean isBusinessAccountHolder) {
        super(username, password, firstName, lastName);
        this.email = email;
        this.customerAddress = customerAddress;
        this.phoneNumber = phoneNumber;
        this.BSN = BSN;
        this.accountManager = accountManager;
        this.isBusinessAccountHolder = isBusinessAccountHolder;
    }

    public Customer(String username, String password, String firstName, String lastName, String email, String phoneNumber, String BSN, Employee accountManager, boolean isBusinessAccountHolder) {
        super(username, password, firstName, lastName);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.BSN = BSN;
        this.accountManager = accountManager;
        this.isBusinessAccountHolder = isBusinessAccountHolder;
    }


    // wordt gebruikt samen met de backing bean
    public Customer() { }


    // GETTERS EN SETTERS
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBSN() {
        return BSN;
    }

    public void setBSN(String BSN) {
        this.BSN = BSN;
    }

    public boolean isBusinessAccountHolder() {
        return isBusinessAccountHolder;
    }

    public void setBusinessAccountHolder(boolean businessAccountHolder) {
        isBusinessAccountHolder = businessAccountHolder;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Employee getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(Employee accountManager) {
        this.accountManager = accountManager;
    }

    public Set<Account> getAccount() {
        return account;
    }

    public void setAccount(Set<Account> account) {
        this.account = account;
    }


    @Override
    public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Customer customer = (Customer) o;
            boolean eq = super.equals(o);
            if (eq && o instanceof Customer) {
                        return isBusinessAccountHolder == customer.isBusinessAccountHolder &&
                        Objects.equals(email, customer.email) &&
                        Objects.equals(customerAddress, customer.customerAddress) &&
                        Objects.equals(phoneNumber, customer.phoneNumber) &&
                        Objects.equals(BSN, customer.BSN) &&
                        Objects.equals(accountManager, customer.accountManager);
            } else{
                return false;
            }
        }


    @Override
    public int hashCode() {
        return Objects.hash(email, customerAddress, phoneNumber, BSN, isBusinessAccountHolder, accountManager, account);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "email='" + email + '\'' +
                ", address='" + customerAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", BSN='" + BSN + '\'' +
                ", isBusinessAccountHolder=" + isBusinessAccountHolder +
                ", accountManager=" + accountManager +
                ", account=" + account +
                ", userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
