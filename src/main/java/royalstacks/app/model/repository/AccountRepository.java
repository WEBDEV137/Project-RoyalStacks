package royalstacks.app.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import royalstacks.app.model.Account;
import royalstacks.app.model.BusinessAccount;
import royalstacks.app.model.User;

import java.util.*;

@Repository
public interface AccountRepository extends CrudRepository<Account,Integer> {


    @Query("SELECT a FROM Account a WHERE a.accountNumber = ?1")
    Optional<Account> getAccountByAccountNumber(String accountNumber);

    @Query("SELECT a.accountNumber FROM Account a WHERE a.accountId = (select max(a.accountId) From a)")
    Optional<String> getLastAccountNumber();

    @Query("SELECT a.accountId FROM Account a WHERE a.accountNumber = ?1")
    Optional<Integer> getAccountIdByAccountNumber(String accountNumber);

    @Query("SELECT a.accountId FROM Account a WHERE a.accountNumber LIKE CONCAT('%%%%%%%%', ?1)")
    Optional<Integer> getAccountIdByNumberExIban(String accountNumber);

    @Query("SELECT p FROM PrivateAccount p")
    Iterable<Account> findAllPrivateAccounts();

    @Query("SELECT b FROM BusinessAccount b")
    Iterable<Account> findAllBusinessAccounts();

    List<Account> findAll();
}
