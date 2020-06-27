package royalstacks.app.model;

import javax.persistence.*;

@Entity
public class CustomerAddress {

    @Id
    @GeneratedValue
    protected int id;
    @OneToOne(mappedBy="customerAddress", cascade = CascadeType.ALL)
    private Customer customer;
    private String postalCode;
    private String houseNumber;
    private String suffix;
    private String city;
    private String street;


    public CustomerAddress(Customer customer, String postalCode, String houseNumber, String suffix, String city, String street) {
        this.customer = customer;
        this.postalCode = postalCode;
        this.houseNumber = houseNumber;
        this.suffix = suffix;
        this.city = city;
        this.street = street;
    }

    public CustomerAddress(String postalCode, String houseNumber, String suffix, String city, String street) {
        this.postalCode = postalCode;
        this.houseNumber = houseNumber;
        this.suffix = suffix;
        this.city = city;
        this.street = street;
    }

    public CustomerAddress(){
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Address{" +
                "postalCode='" + postalCode + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", suffix='" + suffix + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
