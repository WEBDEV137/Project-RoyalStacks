package royalstacks.app.model.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import royalstacks.app.model.Customer;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Query("SELECT u FROM Customer u WHERE u.BSN = ?1")
    Optional<Customer> findCustomerByBSN(String BSN);

    @Query("SELECT c.firstName, c.lastName, SUM(ba.balance) AS totalBalance FROM Customer c JOIN c.account ba WHERE TYPE(ba) = BusinessAccount GROUP BY c.userid ORDER BY totalBalance DESC")
    List<Object[]> findCustomersAndBusinessAccountBalance(Pageable pageable);

    @Query("SELECT c.firstName, c.lastName, SUM(pa.balance) AS totalBalance FROM Customer c JOIN c.account pa WHERE TYPE(pa) = PrivateAccount GROUP BY c.userid ORDER BY totalBalance DESC")
    List<Object[]> findCustomersAndPrivateAccountBalance(Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE c.userid = ?1")
    Optional<Customer> findCustomerByUserId(int userid);

}
