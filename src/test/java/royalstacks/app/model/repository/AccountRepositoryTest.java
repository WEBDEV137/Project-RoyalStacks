package royalstacks.app.model.repository;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import royalstacks.app.model.Account;
import royalstacks.app.model.BusinessAccount;
import royalstacks.app.model.PrivateAccount;
import royalstacks.app.model.Sector;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    AccountRepository accountRepository;

    @Test
    void getAccountByAccountNumber() {
      BusinessAccount businessAccount1 = new BusinessAccount("NL32ROYA0000000019", new BigDecimal("999.99"), "Incentro", "26545784", "NL12345678B01", Sector.ICT_AND_MEDIA);
      BusinessAccount businessAccount2 = new BusinessAccount("NL10ROYA0000000124", new BigDecimal("645.50"), "Picnic", "95626548", "NL23456789B02", Sector.RETAIL);
      PrivateAccount privateAccount1 = new PrivateAccount("NL54ROYA0000038423", new BigDecimal("200.15"));
      PrivateAccount privateAccount2 = new PrivateAccount("NL98ROYA0000038407", new BigDecimal("25"));
      entityManager.persist(businessAccount1);
      entityManager.persist(businessAccount2);
      entityManager.persist(privateAccount1);
      entityManager.persist(privateAccount2);
      Optional<Account> actualB1 = accountRepository.getAccountByAccountNumber("NL32ROYA0000000019");
      Optional<Account> actualB2 = accountRepository.getAccountByAccountNumber("NL10ROYA0000000124");
      Optional<Account> actualP1 = accountRepository.getAccountByAccountNumber("NL54ROYA0000038423");
      Optional<Account> actualP2 = accountRepository.getAccountByAccountNumber("NL98ROYA0000038407");
      assertEquals(Optional.of(businessAccount1), actualB1);
      assertEquals(Optional.of(businessAccount2), actualB2);
      assertEquals(Optional.of(privateAccount1), actualP1);
      assertEquals(Optional.of(privateAccount2), actualP2);
      assertNotEquals(Optional.of(businessAccount1), actualB2);
      assertNotEquals(Optional.of(privateAccount1), actualP2);
    }

    @Test
    void getLastAccountNumber() {
        BusinessAccount businessAccount1 = new BusinessAccount("NL32ROYA0000000019", new BigDecimal("999.99"), "Incentro", "26545784", "NL12345678B01", Sector.ICT_AND_MEDIA);
        BusinessAccount businessAccount2 = new BusinessAccount("NL10ROYA0000000124", new BigDecimal("645.50"), "Picnic", "95626548", "NL23456789B02", Sector.RETAIL);
        PrivateAccount privateAccount1 = new PrivateAccount("NL54ROYA0000038423", new BigDecimal("200.15"));
        PrivateAccount privateAccount2 = new PrivateAccount("NL98ROYA0000038407", new BigDecimal("25"));
        entityManager.persist(businessAccount1);
        entityManager.persist(businessAccount2);
        entityManager.persist(privateAccount1);
        entityManager.persist(privateAccount2);

        Optional<String> actualLast = accountRepository.getLastAccountNumber();
        assertEquals(Optional.of(privateAccount2.getAccountNumber()), actualLast);
        entityManager.persist(businessAccount1);
        assertNotEquals(Optional.of(businessAccount1), actualLast);
        assertEquals(Optional.of(privateAccount2.getAccountNumber()), actualLast);
    }

    @Test
    void getAccountIdByAccountNumber() {
        BusinessAccount businessAccount1 = new BusinessAccount("NL32ROYA0000000019", new BigDecimal("999.99"), "Incentro", "26545784", "NL12345678B01", Sector.ICT_AND_MEDIA);
        BusinessAccount businessAccount2 = new BusinessAccount("NL10ROYA0000000124", new BigDecimal("645.50"), "Picnic", "95626548", "NL23456789B02", Sector.RETAIL);
        PrivateAccount privateAccount1 = new PrivateAccount("NL54ROYA0000038423", new BigDecimal("200.15"));
        PrivateAccount privateAccount2 = new PrivateAccount("NL98ROYA0000038407", new BigDecimal("25"));
        entityManager.persist(businessAccount1);
        entityManager.persist(businessAccount2);
        entityManager.persist(privateAccount1);
        entityManager.persist(privateAccount2);

        Optional<Integer> actualAccountId1 = accountRepository.getAccountIdByAccountNumber("NL32ROYA0000000019");
        Optional<Integer> actualAccountId2 = accountRepository.getAccountIdByAccountNumber("NL98ROYA0000038407");

        assertNotEquals(1, actualAccountId1);
        assertNotEquals(4, actualAccountId2);
    }

    @Test
    void getAccountIdByNumberExIban() {
        BusinessAccount businessAccount1 = new BusinessAccount("NL32ROYA0000000019", new BigDecimal("999.99"), "Incentro", "26545784", "NL12345678B01", Sector.ICT_AND_MEDIA);
        BusinessAccount businessAccount2 = new BusinessAccount("NL10ROYA0000000124", new BigDecimal("645.50"), "Picnic", "95626548", "NL23456789B02", Sector.RETAIL);
        PrivateAccount privateAccount1 = new PrivateAccount("NL54ROYA0000038423", new BigDecimal("200.15"));
        PrivateAccount privateAccount2 = new PrivateAccount("NL98ROYA0000038407", new BigDecimal("25"));
        entityManager.persist(businessAccount1);
        entityManager.persist(businessAccount2);
        entityManager.persist(privateAccount1);
        entityManager.persist(privateAccount2);

        Optional<Integer> actualAccountId1 = accountRepository.getAccountIdByAccountNumber("0000000019");
        Optional<Integer> actualAccountId2 = accountRepository.getAccountIdByAccountNumber("0000038407");

        assertNotEquals(1, actualAccountId1);
        assertNotEquals(4, actualAccountId2);
    }

    @Test
    void findAllPrivateAccounts() {
        BusinessAccount businessAccount1 = new BusinessAccount("NL32ROYA0000000019", new BigDecimal("999.99"), "Incentro", "26545784", "NL12345678B01", Sector.ICT_AND_MEDIA);
        BusinessAccount businessAccount2 = new BusinessAccount("NL10ROYA0000000124", new BigDecimal("645.50"), "Picnic", "95626548", "NL23456789B02", Sector.RETAIL);
        BusinessAccount businessAccount3 = new BusinessAccount("NL30ROYA0000005487", new BigDecimal("0.0"), "Jumbo", "45458525", "NL34567890B01", Sector.RETAIL);
        BusinessAccount businessAccount4 = new BusinessAccount("NL94ROYA0000044008", new BigDecimal("5000"), "Blokker", "95685632", "NL45678901B01", Sector.RETAIL);
        BusinessAccount businessAccount5 = new BusinessAccount("NL49ROYA0000005436", new BigDecimal("3450.50"), "Hans Schilderwerken", "95956568", "NL56789012B01", Sector.CONSTRUCTION);
        PrivateAccount privateAccount1 = new PrivateAccount("NL54ROYA0000038423", new BigDecimal("200.15"));
        PrivateAccount  privateAccount2 = new PrivateAccount("NL98ROYA0000038407", new BigDecimal("25"));
        PrivateAccount  privateAccount3 = new PrivateAccount("NL82ROYA0000038598", new BigDecimal("998865400.75"));
        PrivateAccount  privateAccount4 = new PrivateAccount("NL70ROYA0000034061", new BigDecimal("500000.80"));
        PrivateAccount  privateAccount5 = new PrivateAccount("NL80ROYA0000031941", new BigDecimal("450.56"));
        entityManager.persist(businessAccount1);
        entityManager.persist(businessAccount2);
        entityManager.persist(businessAccount3);
        entityManager.persist(businessAccount4);
        entityManager.persist(businessAccount5);
        entityManager.persist(privateAccount1);
        entityManager.persist(privateAccount2);
        entityManager.persist(privateAccount3);
        entityManager.persist(privateAccount4);
        entityManager.persist(privateAccount5);

        Iterable<Account> actualReturn = accountRepository.findAllPrivateAccounts();
        int numberOfAccounts = 0;
        for(Account account : actualReturn){
            assertTrue(account instanceof PrivateAccount);
            numberOfAccounts++;
        }
        assertEquals(5, numberOfAccounts);
    }

    @Test
    void findAllBusinessAccounts() {
        BusinessAccount businessAccount1 = new BusinessAccount("NL32ROYA0000000019", new BigDecimal("999.99"), "Incentro", "26545784", "NL12345678B01", Sector.ICT_AND_MEDIA);
        BusinessAccount businessAccount2 = new BusinessAccount("NL10ROYA0000000124", new BigDecimal("645.50"), "Picnic", "95626548", "NL23456789B02", Sector.RETAIL);
        BusinessAccount businessAccount3 = new BusinessAccount("NL30ROYA0000005487", new BigDecimal("0.0"), "Jumbo", "45458525", "NL34567890B01", Sector.RETAIL);
        BusinessAccount businessAccount4 = new BusinessAccount("NL94ROYA0000044008", new BigDecimal("5000"), "Blokker", "95685632", "NL45678901B01", Sector.RETAIL);
        BusinessAccount businessAccount5 = new BusinessAccount("NL49ROYA0000005436", new BigDecimal("3450.50"), "Hans Schilderwerken", "95956568", "NL56789012B01", Sector.CONSTRUCTION);
        PrivateAccount privateAccount1 = new PrivateAccount("NL54ROYA0000038423", new BigDecimal("200.15"));
        PrivateAccount  privateAccount2 = new PrivateAccount("NL98ROYA0000038407", new BigDecimal("25"));
        PrivateAccount  privateAccount3 = new PrivateAccount("NL82ROYA0000038598", new BigDecimal("998865400.75"));
        PrivateAccount  privateAccount4 = new PrivateAccount("NL70ROYA0000034061", new BigDecimal("500000.80"));
        PrivateAccount  privateAccount5 = new PrivateAccount("NL80ROYA0000031941", new BigDecimal("450.56"));
        entityManager.persist(businessAccount1);
        entityManager.persist(businessAccount2);
        entityManager.persist(businessAccount3);
        entityManager.persist(businessAccount4);
        entityManager.persist(businessAccount5);
        entityManager.persist(privateAccount1);
        entityManager.persist(privateAccount2);
        entityManager.persist(privateAccount3);
        entityManager.persist(privateAccount4);
        entityManager.persist(privateAccount5);

        Iterable<Account> actualAccounts = accountRepository.findAllBusinessAccounts();

        int numberOfAccounts = 0;
        for(Account account : actualAccounts){
            assertTrue(account instanceof BusinessAccount);
            numberOfAccounts++;
        }
        assertEquals(5, numberOfAccounts);

    }

    @Test
    void findAll() {
        BusinessAccount businessAccount1 = new BusinessAccount("NL32ROYA0000000019", new BigDecimal("999.99"), "Incentro", "26545784", "NL12345678B01", Sector.ICT_AND_MEDIA);
        BusinessAccount businessAccount2 = new BusinessAccount("NL10ROYA0000000124", new BigDecimal("645.50"), "Picnic", "95626548", "NL23456789B02", Sector.RETAIL);
        BusinessAccount businessAccount3 = new BusinessAccount("NL30ROYA0000005487", new BigDecimal("0.0"), "Jumbo", "45458525", "NL34567890B01", Sector.RETAIL);
        BusinessAccount businessAccount4 = new BusinessAccount("NL94ROYA0000044008", new BigDecimal("5000"), "Blokker", "95685632", "NL45678901B01", Sector.RETAIL);
        BusinessAccount businessAccount5 = new BusinessAccount("NL49ROYA0000005436", new BigDecimal("3450.50"), "Hans Schilderwerken", "95956568", "NL56789012B01", Sector.CONSTRUCTION);
        PrivateAccount privateAccount1 = new PrivateAccount("NL54ROYA0000038423", new BigDecimal("200.15"));
        PrivateAccount  privateAccount2 = new PrivateAccount("NL98ROYA0000038407", new BigDecimal("25"));
        PrivateAccount  privateAccount3 = new PrivateAccount("NL82ROYA0000038598", new BigDecimal("998865400.75"));
        PrivateAccount  privateAccount4 = new PrivateAccount("NL70ROYA0000034061", new BigDecimal("500000.80"));
        PrivateAccount  privateAccount5 = new PrivateAccount("NL80ROYA0000031941", new BigDecimal("450.56"));
        entityManager.persist(businessAccount1);
        entityManager.persist(businessAccount2);
        entityManager.persist(businessAccount3);
        entityManager.persist(businessAccount4);
        entityManager.persist(businessAccount5);
        entityManager.persist(privateAccount1);
        entityManager.persist(privateAccount2);
        entityManager.persist(privateAccount3);
        entityManager.persist(privateAccount4);
        entityManager.persist(privateAccount5);

        Iterable<Account> actualAccounts = accountRepository.findAll();
        int numberOfAccounts = 0;
        for(Account account : actualAccounts){
            assertTrue(account instanceof Account);
            numberOfAccounts++;
        }
        assertEquals(10, numberOfAccounts);



    }
}