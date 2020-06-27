package royalstacks.app.model.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import royalstacks.app.model.BusinessAccount;
import royalstacks.app.model.Sector;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BusinessAccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BusinessAccountRepository businessAccountRepository;

    @Test
    public void findSectorAndAverageBalanceTest() {

        //ARRANGE
        BusinessAccount businessAccount1 = new BusinessAccount("1", new BigDecimal(100000.00), "test1", "12345678", "NL12345678B01", Sector.AGRICULTURE);
        this.entityManager.persist(businessAccount1);
        BusinessAccount businessAccount2 = new BusinessAccount("2", new BigDecimal(5.00), "test2", "23456789", "NL23456789B01", Sector.AGRICULTURE);
        this.entityManager.persist(businessAccount2);
        BusinessAccount businessAccount3 = new BusinessAccount("3", new BigDecimal(5500.00), "test3", "34567890", "NL34567890B01", Sector.CONSTRUCTION);
        this.entityManager.persist(businessAccount3);
        BusinessAccount businessAccount4 = new BusinessAccount("4", new BigDecimal(666.66), "test4", "45678901", "NL45678901B01", Sector.CONSTRUCTION);
        this.entityManager.persist(businessAccount4);
        BusinessAccount businessAccount5 = new BusinessAccount("5", new BigDecimal(1.00), "test5", "56789012", "NL56789012B01", Sector.RETAIL);
        this.entityManager.persist(businessAccount5);

        //ACT
        //Group by
        List<Object[]> testList = businessAccountRepository.findSectorAndAverageBalance();
        int actual = testList.size();
        int expected = 3;
        //Average balance and Order by
        double actual2 = (double) testList.get(0)[1];
        double actual3 = (double) testList.get(1)[1];
        double actual4 = (double) testList.get(2)[1];
        double expected2 = 50002.5;
        double expected3 = 3083.33;
        double expected4 = 1.0;

        //ASSERT
        //Group by
        assertEquals(expected, actual);
        //Average balance and (implicitly also) Order by
        assertEquals(expected2, actual2, 0);
        assertEquals(expected3, actual3, 0);
        assertEquals(expected4, actual4, 0);
    }

    @Test
    public void findCompaniesAndBusinessAccountsTest() {

        //ARRANGE
        BusinessAccount businessAccount1 = new BusinessAccount("NL10ROYA0000000124", new BigDecimal(12500.00), "CompanyOne", "12345678", "NL014948588B01", Sector.BUSINESS_SERVICES);
        this.entityManager.persist(businessAccount1);
        BusinessAccount businessAccount2 = new BusinessAccount("NL85ROYA0000000132", new BigDecimal(500.00), "CompanyTwo", "23456789", "NL141279424B01", Sector.INDUSTRY);
        this.entityManager.persist(businessAccount2);
        BusinessAccount businessAccount3 = new BusinessAccount("NL44ROYA0000000191", new BigDecimal(1000000.00), "CompanyThree", "34567890", "NL005565650B01", Sector.ICT_AND_MEDIA);
        this.entityManager.persist(businessAccount3);

        //ACT
        List<Object[]> testList = businessAccountRepository.findCompaniesAndBusinessAccounts();
        int actual = testList.size();
        int expected = 3;

        //ASSERT
        assertEquals(expected, actual);
    }
}