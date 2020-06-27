package royalstacks.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import royalstacks.app.model.AccountHolderInvite;
import royalstacks.app.model.Customer;
import royalstacks.app.model.repository.AccountHolderInviteRepository;

import java.util.Optional;

@Service
public class AccountHolderInviteService {

    @Autowired
    AccountHolderInviteRepository accountHolderInviteRepository;

        public void saveAccountHolderInvite(AccountHolderInvite ahi){
        accountHolderInviteRepository.save(ahi);
        System.out.println("Saved to DB");
    }

    public Optional<AccountHolderInvite> findInviteByAccountAndInvitee(int userId, int accountId){
            return accountHolderInviteRepository.findInviteByAccountAndInvitee(userId, accountId);
    }

    public Optional<AccountHolderInvite> findAccountHolderInviteByAccountAndInviteeAndCode(int userId, int accountId, String verificationCode){
        return accountHolderInviteRepository.findAccountHolderInviteByAccountAndInviteeAndCode(userId, accountId, verificationCode);
    }

    public boolean isVerificationCodeFormatValid(String verificationCode){
        verificationCode = verificationCode.trim();
        return verificationCode.matches("\\d{5}");
    }



}
