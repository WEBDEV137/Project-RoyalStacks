package royalstacks.app.service;

import org.assertj.core.internal.cglib.asm.$AnnotationVisitor;
import org.junit.Before;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.test.context.junit4.SpringRunner;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import royalstacks.app.model.*;
import royalstacks.app.model.repository.AccountRepository;

import royalstacks.app.model.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class TransactionServiceTest {

    AccountRepository accountRepository;

    @Mock
    TransactionRepository transactionRepository;


    private AccountService accountService = Mockito.mock(AccountService.class);

    @InjectMocks
    TransactionService transactionService;
    private Object Account;

    @Test
    void saveTransaction() {
    }

    @Test
    void executeTransaction() {
    }




    @Test //TEST TO DETERMINE IF IF GET AN ACTUAL LIST OF TEN TRANSACTION
    void getTenTransaction() {
        List<Transaction>  transactions = new ArrayList<>();
        for(int index = 0; index < 20; index ++){
            Transaction transaction = new Transaction();
            transactions.add(transaction);
        }
        List<Transaction>  transactions1 = new ArrayList<>();
        for(int index = 0; index < 5; index ++){
            Transaction transaction = new Transaction();
            transactions1.add(transaction);
        }

        List<Transaction>  transactions2 = new ArrayList<>();
        for(int index = 0; index < 10; index ++){
            Transaction transaction = new Transaction();
            transactions2.add(transaction);
        }

        List<Transaction>  transactions3 = new ArrayList<>();


        Mockito.when(transactionRepository.getTransactionsByFromAccountIdOrToAccountIdOrderByDateDesc(1,1)).thenReturn(transactions);
        Mockito.when(transactionRepository.getTransactionsByFromAccountIdOrToAccountIdOrderByDateDesc(2,2)).thenReturn(transactions1);
        Mockito.when(transactionRepository.getTransactionsByFromAccountIdOrToAccountIdOrderByDateDesc(3,3)).thenReturn(transactions2);
        Mockito.when(transactionRepository.getTransactionsByFromAccountIdOrToAccountIdOrderByDateDesc(4,4)).thenReturn(transactions3);

        List<AccountHolderTransaction> transactionListActual = transactionService.getTenLastTransaction(1);
        List<AccountHolderTransaction> transactionListActual1 = transactionService.getTenLastTransaction(2);
        List<AccountHolderTransaction> transactionListActual2 = transactionService.getTenLastTransaction(3);
        List<AccountHolderTransaction> transactionListActual3 = transactionService.getTenLastTransaction(4);

        int transactionListSizeActual = transactionListActual.size();
        int transactionListSizeActual1 =transactionListActual1.size();
        int transactionListSizeActual2 =transactionListActual2.size();
        int transactionListSizeActual3 =transactionListActual3.size();

        int transactionListSizeExpected = 10;
        int transactionListSizeExpected1 = 5;
        int transactionListSizeExpected2 = 10;
        int transactionListSizeExpected3 = 0;


        assertEquals(transactionListSizeExpected,transactionListSizeActual);
        assertEquals(transactionListSizeExpected1,transactionListSizeActual1);
        assertEquals(transactionListSizeExpected2,transactionListSizeActual2);
        assertEquals(transactionListSizeExpected3,transactionListSizeActual3);


        //check if size of transactionlist is actually 10
    }

// TEST TO SEE IF AMOUNT IS CORRECTLY DEBITET OR CREDITET
    @Test
    void DebitOrCredit(){
        //SET UP

        PrivateAccount account = new PrivateAccount("NL79ROYA1111111111",new BigDecimal(111));
        int acountid = 1;
        account.setAccountId(1);

        Transaction transaction = new Transaction(null,0,0,null,"", LocalDateTime.parse("1986-04-08T12:30:00"));


        //SO WHEN SHOULD BE A CREDIT

        transaction.setToAccountId(1);
        transaction.setAmount(new BigDecimal(500));
        Mockito.when(accountService.getAccountById(1)).thenReturn(account);
        AccountHolderTransaction accountHolderTransaction =  transactionService.getTransaction(transaction, acountid);

        BigDecimal amountExpected = transaction.getAmount();
        BigDecimal amountActual = accountHolderTransaction.getCredit();
        BigDecimal debit = accountHolderTransaction.getDebit();
        assertEquals(amountExpected,amountActual);
        assertNull(debit);

        //THIS TIME SHOULD BE A DEBIT

        Transaction transaction2 = new Transaction(null,0,0,new BigDecimal(500),"", LocalDateTime.parse("1986-04-08T12:30:00"));
        transaction2.setFromAccountId(1);
        AccountHolderTransaction accountHolderTransaction2 =  transactionService.getTransaction(transaction2, acountid);
        BigDecimal amountActual2 = accountHolderTransaction2.getDebit();
        assertEquals(amountExpected,amountActual2);
        BigDecimal credit = accountHolderTransaction2.getCredit();
        assertNull(credit);

    }






}