package royalstacks.app.model;

import java.math.BigDecimal;

public class CustomerAndTotalBalance {

    private String firstName;
    private String lastName;
    private BigDecimal totalBalance;

    //CONSTRUCTOR
    public CustomerAndTotalBalance(String firstName, String lastName, BigDecimal totalBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalBalance = totalBalance;
    }

    //GETTERS
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

}
