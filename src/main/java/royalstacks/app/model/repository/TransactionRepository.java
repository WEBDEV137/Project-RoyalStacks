package royalstacks.app.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import royalstacks.app.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> getTransactionsByFromAccountIdOrToAccountIdOrderByDateDesc(int fromAccountId, int toAccountId);

    int countByFromAccountIdOrToAccountId(int fromAccountId, int toAccountId);

}
