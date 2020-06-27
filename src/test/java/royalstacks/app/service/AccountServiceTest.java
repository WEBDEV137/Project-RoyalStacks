package royalstacks.app.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import royalstacks.app.model.BusinessAccount;
import royalstacks.app.model.PrivateAccount;
import royalstacks.app.model.Sector;
import royalstacks.app.model.repository.AccountRepository;


import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;
    @InjectMocks
    AccountService accountService;


    @Test
    public void retrieveLastIban() {
        PrivateAccount privateAccount1 = new PrivateAccount("NL79ROYA1111111111", new BigDecimal(111));
        Optional<String> optionalAccount1 = Optional.of(privateAccount1.getAccountNumber());
        Mockito.when(accountRepository.getLastAccountNumber()).thenReturn(optionalAccount1);
        String lastIban = accountService.retrieveLastIban();
        assertEquals(privateAccount1.getAccountNumber(), lastIban);
    }


   @Test
    public void createNewIban() {
       PrivateAccount privateAccount1 = new PrivateAccount("NL79ROYA1111111111", new BigDecimal(111));
       Optional<String> optionalAccount1 = Optional.of(privateAccount1.getAccountNumber());
       Mockito.when(accountRepository.getLastAccountNumber()).thenReturn(optionalAccount1);
       String expectedIban = "NL60ROYA1111111138";
       String actualIban = accountService.createNewIban();
       assertEquals(expectedIban, actualIban);
    }


    @Test
    public void incrementIbanByOne() {
        String accountNr1 = "0000000000";
        String accountNr2 = "0000000009";
        String accountNr3 = "0000000099";
        String accountNr4 = "0000000100";
        String accountNr5 = "0000009999";
        String accountNr6 = "0999899999";
        String accountNr7 = "0999999999";

        String accountNrExpected1 = "0000000001";
        String accountNrExpected2 = "0000000010";
        String accountNrExpected3 = "0000000100";
        String accountNrExpected4 = "0000000101";
        String accountNrExpected5 = "0000010000";
        String accountNrExpected6 = "0999900000";
        String accountNrExpected7 = "1000000000";


        assertEquals(accountNrExpected1, accountService.incrementAccountNrByOne(accountNr1));
        assertEquals(accountNrExpected2, accountService.incrementAccountNrByOne(accountNr2));
        assertEquals(accountNrExpected3, accountService.incrementAccountNrByOne(accountNr3));
        assertEquals(accountNrExpected4, accountService.incrementAccountNrByOne(accountNr4));
        assertEquals(accountNrExpected5, accountService.incrementAccountNrByOne(accountNr5));
        assertEquals(accountNrExpected6, accountService.incrementAccountNrByOne(accountNr6));
        assertEquals(accountNrExpected7, accountService.incrementAccountNrByOne(accountNr7));
    }

    @Test
    public void createControlNrFromAccountNr() {
        String actualControlNr1 = accountService.createControlNrFromAccountNr("0001234567");
        String actualControlNr2 = accountService.createControlNrFromAccountNr("0009999999");
        String actualControlNr3 = accountService.createControlNrFromAccountNr("5555555555");
        String actualControlNr4 = accountService.createControlNrFromAccountNr("1784987656");
        String actualControlNr5 = accountService.createControlNrFromAccountNr("1000000000");
        String actualControlNr6 = accountService.createControlNrFromAccountNr("8787878787");

        String expectedControlNr1 = "25";
        String expectedControlNr2 = "72";
        String expectedControlNr3 = "19";
        String expectedControlNr4 = "46";
        String expectedControlNr5 = "15";
        String expectedControlNr6 = "41";

        assertEquals(expectedControlNr1, actualControlNr1);
        assertEquals(expectedControlNr2, actualControlNr2);
        assertEquals(expectedControlNr3, actualControlNr3);
        assertEquals(expectedControlNr4, actualControlNr4);
        assertEquals(expectedControlNr5, actualControlNr5);
        assertEquals(expectedControlNr6, actualControlNr6);
    }

    @Test
    public void passed11Test() {
        assertFalse(accountService.passed11Test("0000000100"));
        assertFalse(accountService.passed11Test("0034567896"));
        assertFalse(accountService.passed11Test("1234567899"));
        assertFalse(accountService.passed11Test("6789098765"));
        assertFalse(accountService.passed11Test("9876543211"));

        assertTrue(accountService.passed11Test("9876543210"));
        assertTrue(accountService.passed11Test("6555554436"));
        assertTrue(accountService.passed11Test("2222222222"));
        assertTrue(accountService.passed11Test("1111111111"));
        assertTrue(accountService.passed11Test("5555555504"));
        assertTrue(accountService.passed11Test("8888888888"));

    }

    @Test
    public void makeIban11TestProof() {
        assertEquals("0000000108", accountService.makeAccountNr11TestProof("0000000100"));
        assertEquals("5555555504", accountService.makeAccountNr11TestProof("5555555500"));
        assertEquals("8888888888", accountService.makeAccountNr11TestProof("8888888883"));
        assertEquals("6555554436", accountService.makeAccountNr11TestProof("6555554433"));
        assertEquals("1111111111", accountService.makeAccountNr11TestProof("1111111110"));
    }
}