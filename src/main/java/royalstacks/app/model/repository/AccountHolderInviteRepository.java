package royalstacks.app.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import royalstacks.app.model.AccountHolderInvite;

import java.util.Optional;

@Repository
public interface AccountHolderInviteRepository extends CrudRepository<AccountHolderInvite, Integer> {

    @Query("SELECT i FROM AccountHolderInvite i WHERE i.invitee.userid = ?1 AND i.account.accountId = ?2")
    Optional<AccountHolderInvite> findInviteByAccountAndInvitee(int userId, int accountId);

    @Query("SELECT i FROM AccountHolderInvite i WHERE i.invitee.userid = ?1 AND i.account.accountId = ?2 AND i.verificationCode = ?3")
    Optional<AccountHolderInvite> findAccountHolderInviteByAccountAndInviteeAndCode(int userId, int accountId, String verificationCode);

}
