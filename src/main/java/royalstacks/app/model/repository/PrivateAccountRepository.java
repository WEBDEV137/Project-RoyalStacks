package royalstacks.app.model.repository;

import org.springframework.data.repository.CrudRepository;
import royalstacks.app.model.PrivateAccount;

public interface PrivateAccountRepository extends CrudRepository<PrivateAccount, Integer> {
}
