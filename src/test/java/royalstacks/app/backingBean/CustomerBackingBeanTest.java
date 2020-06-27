/*
package royalstacks.app.backingBean;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import royalstacks.app.model.Customer;

import static org.junit.jupiter.api.Assertions.*;

class CustomerBackingBeanTest {

    @Test
    void customerCorrectBean() {

        CustomerBackingBean cbb = new CustomerBackingBean("MiekeG!", "Oli3b@l",
                "Mieke", "Geelen", "miekegeelen@gmail.com", "4578 DG",
                "24", "a", "Heerlen", "06789512348", "954786327");


        //actual
        Customer testCustomer = cbb.customer();

        //expected
        Customer customer = new Customer("MiekeG!", "Oli3b@l",
                "Mieke", "Geelen", "miekegeelen@gmail.com", "4578 DG","24", "a",
                "Heerlen", "06789512348", "954786327", null, false);

        assertEquals(customer, testCustomer);
    }

        @Test
        void customerIncorrectBean() {

        CustomerBackingBean cbb2 = new CustomerBackingBean("MiekeG!", "Oli3b@l",
                "Geelen", "Mieke", "miekegeelen@gmail.com","4578 DG", "24", "a",
                "Heerlen", "06789512348", "954786327");

        Customer customer2 = new Customer("MiekeG!", "Oli3b@l",
                "Mieke", "Geelen", "miekegeelen@gmail.com", "4578 DG", "24", "a",
                "Heerlen", "06789512348", "954786327", null, false);

        Customer testCustomer2 = cbb2.customer();
        Assert.assertNotEquals(customer2, testCustomer2);

*/
/*        cbb.setHouseNumber("4578 DG");
        cbb.setPostalCode("Westerlaan 24");
        customer.setHouseNumber("4578 DG");
        customer.setPostalCode("Westerlaan 24");
        Customer testCustomer3 = cbb.customer();

        Assert.assertEquals(customer, testCustomer3);*//*

    }
}*/
