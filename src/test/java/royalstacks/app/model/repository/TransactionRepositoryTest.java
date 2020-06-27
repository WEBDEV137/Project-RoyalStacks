package royalstacks.app.model.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import royalstacks.app.model.Account;
import royalstacks.app.model.PrivateAccount;
import royalstacks.app.model.Transaction;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void countByFromAccountIdOrToAccountIdTest(@Autowired MongoTemplate mongoTemplate) {
        //ARRANGE
        Account accountFrom = new PrivateAccount("NL32ROYA0000000019", new BigDecimal(500.00));
        accountFrom.setAccountId(13);
        Account accountTo = new PrivateAccount("NL44ROYA0000000094", new BigDecimal(30.00));
        accountTo.setAccountId(666);
        Transaction transaction1 = new Transaction(13,666,new BigDecimal(10.00),"test transactie");
        mongoTemplate.save(transaction1);
        Transaction transaction2 = new Transaction(13,666, new BigDecimal(2.50),"nog een test transactie");
        mongoTemplate.save(transaction2);

        //ACT
        int actual = transactionRepository.countByFromAccountIdOrToAccountId(13,666);
        int expected = 2;

        //ASSERT
        assertEquals(expected,actual);
    }

    @Test
    public void getTransaction(@Autowired MongoTemplate mongoTemplate) {


        Transaction transaction = new Transaction(5,2,
                new BigDecimal("200"),"",null);

        transaction.setTransactionId("200");
        mongoTemplate.save(transaction);

        Optional<Transaction> transactionFound = transactionRepository.findById("200");

        //GET accountId and FromAccountid
        int transactionFrom = 0;
        String transactionId = null;
        if(transactionFound.isPresent()){
            transactionId = transactionFound.get().getTransactionId();
            transactionFrom = transactionFound.get().getFromAccountId();
        }

        //EXPECTED accountId
        String expected = "200";

        //expected fromAccountId
        int expectedFrom = 5;

        assertThat(transactionId).isEqualTo(expected);
        assertThat(transactionFrom).isEqualTo(expectedFrom);



    }
}