package royalstacks.app.backingBean;

import royalstacks.app.model.Account;
import royalstacks.app.model.CustomerAddress;
import royalstacks.app.model.Customer;
import royalstacks.app.model.Employee;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

public class CustomerBackingBean {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String postalCode;
    private String houseNumber;
    private String suffix;
    private String city;
    private String street;
    private String phoneNumber;
    private String bsn;
    private Customer customer;
    private boolean isBusinessAccountHolder;
    @ManyToOne
    private Employee accountManager;
    @ManyToMany
    private Set<Account> account;

    public CustomerBackingBean(String username, String password, String firstName, String lastName, String email, String postalCode, String houseNumber, String suffix, String city, String street, String phoneNumber, String bsn) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.postalCode = postalCode;
        this.houseNumber = houseNumber;
        this.suffix = suffix;
        this.city = city;
        this.street = street;
        this.phoneNumber = phoneNumber;
        this.bsn = bsn;
    }

    public Customer customer() {
        this.setCustomer();
        this.customer.setCustomerAddress(new CustomerAddress(customer, postalCode, houseNumber, suffix, city, street));
        return this.customer;
    }

    public void setCustomer() {
        this.customer = new Customer(username, password, firstName, lastName, email, phoneNumber, bsn, null, false);;
    }

    // setters (for testing purposes)
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBSN() {
        return bsn;
    }

    public void setBSN(String BSN) {
        this.bsn = BSN;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isBusinessAccountHolder() {
        return isBusinessAccountHolder;
    }

    public void setBusinessAccountHolder(boolean businessAccountHolder) {
        isBusinessAccountHolder = businessAccountHolder;
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
    public String toString() {
        return "CustomerBackingBean{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", suffix='" + suffix + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bsn='" + bsn + '\'' +
                ", isBusinessAccountHolder=" + isBusinessAccountHolder +
                ", accountManager=" + accountManager +
                ", account=" + account +
                '}';
    }
}


