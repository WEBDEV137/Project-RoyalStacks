package royalstacks.app.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import royalstacks.app.model.pos.ConnectionRequest;

import java.util.Optional;

@Repository
public interface ConnectionRequestRepository extends CrudRepository<ConnectionRequest, Integer> {

    @Query("SELECT c FROM ConnectionRequest c WHERE c.businessAccountNumber LIKE CONCAT('%%%%%%%%', ?1)")
    Optional<ConnectionRequest> findCustomerRequestByBusinessAccountIban(String accountNumber);


}