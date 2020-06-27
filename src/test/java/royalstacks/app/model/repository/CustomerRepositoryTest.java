package royalstacks.app.model.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import royalstacks.app.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BusinessAccountRepository businessAccountRepository;

    @Test
    public void findCustomersAndBusinessAccountBalanceTest() {

        //ARRANGE
        Customer customer1 = new Customer("DarthVader","ewoksrule",
                "Anakin","Skywalker","ewoksrule@test.nl",
                new CustomerAddress(), "0201234567","123456789",
                new Employee(),true);
        this.entityManager.persist(customer1);

        BusinessAccount businessAccount = new BusinessAccount("1", new BigDecimal(500.00),"DarthBV","12345678","NL12345678B01", Sector.AGRICULTURE);
        businessAccount.addAccountHolder(customer1);
        this.entityManager.persist(businessAccount);

        Customer customer2 = new Customer("QueenLeia","hansoloforever",
                "Leia","Organa","QueenLeia@test.nl",
                new CustomerAddress(), "0202345678","890123456",
                new Employee(),true);
        this.entityManager.persist(customer2);

        BusinessAccount businessAccount2 = new BusinessAccount("2", new BigDecimal(100000.00),"LeiaBV","23456789","NL23456789B01", Sector.HOSPITALITY);
        businessAccount2.addAccountHolder(customer2);
        this.entityManager.persist(businessAccount2);

        BusinessAccount businessAccount3 = new BusinessAccount("3", new BigDecimal(5609.45),"LeiaNV","34567890","NL34567890B01", Sector.CONSTRUCTION);
        businessAccount3.addAccountHolder(customer2);
        this.entityManager.persist(businessAccount3);

        //ACT
        //Group by
        Pageable pageable = PageRequest.of(0, 10);
        List<Object[]> testList = customerRepository.findCustomersAndBusinessAccountBalance(pageable);
        int actual = testList.size();
        int expected = 2;
        //First name, last name and total balance and (implicitly also) Order by
        String actual2 = (String) testList.get(0)[0];
        String expected2 = "Leia";
        String actual3 = (String) testList.get(0)[1];
        String expected3 = "Organa";
        BigDecimal actual4 = (BigDecimal) testList.get(0)[2];
        BigDecimal expected4 = new BigDecimal(105609.45);

        //ASSERT
        //Group by
        assertEquals(expected, actual);
        //First name, last name and total balance and (implicitly also) Order by
        assertEquals(expected2,actual2);
        assertEquals(expected3,actual3);
        assertEquals(expected4.doubleValue(),actual4.doubleValue(), 0.1);
    }

}
