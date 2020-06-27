package royalstacks.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import royalstacks.app.model.Account;
import royalstacks.app.model.BusinessAccount;
import royalstacks.app.model.Customer;
import royalstacks.app.model.repository.AccountRepository;
import royalstacks.app.model.repository.EmployeeRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    public <T extends Account> void saveAccount(T account) {
        accountRepository.save(account);}

    public Optional<Account> getAccountByIban(String accountNumber){
        return accountRepository.getAccountByAccountNumber(accountNumber);
    }
    public String retrieveLastIban(){
        Optional<String> accountNumber = accountRepository.getLastAccountNumber();
        return accountNumber.orElse(null);
    }

    public Optional<Integer> getAccountIdByNumberExIban(String accountNumber){
        return accountRepository.getAccountIdByNumberExIban(accountNumber);
    }

    public String createNewIban(){
        final String INITIAL_ACCOUNT = "XX00XXX0000000000";
        final String LANDCODE = "NL";
        final String BANKCODE = "ROYA";
        String lastIban;

        if(retrieveLastIban() == null){
            lastIban = INITIAL_ACCOUNT;
        }
        else {
            lastIban = retrieveLastIban();
        }
        String newIban = incrementAccountNrByOne(lastIban.substring(8));
        String accountNr11TestProof = makeAccountNr11TestProof(newIban);
        String controlNr = createControlNrFromAccountNr(accountNr11TestProof);
        return  LANDCODE + controlNr + BANKCODE + accountNr11TestProof;
    }

    public String incrementAccountNrByOne(String accountNr){
        BigInteger bigInteger = new BigInteger(accountNr).add(BigInteger.ONE);
        return  String.format("%010d", bigInteger);
    }

    public String createControlNrFromAccountNr(String accountNr){
    final String COUNTRYCODE_NR = "232100";
    final String BANKCODE_NR = "27243410";
    final int HASH_BASE = 98;
    final int HASH_MODULUS = 97;

    BigInteger bigInteger = new BigInteger(BANKCODE_NR + accountNr + COUNTRYCODE_NR);
    BigInteger result = BigInteger.valueOf(HASH_BASE).subtract(bigInteger.mod(BigInteger.valueOf(HASH_MODULUS)));
    return String.format("%02d", result);
    }

    public boolean passed11Test(String accountNr){
        int testSum = 0;

        for (int index = 0; index < accountNr.length(); index++) {
            int singleDigit = Character.getNumericValue(accountNr.charAt(index));
            testSum += (singleDigit * (accountNr.length()-index));
        }
        return testSum%11 == 0;
    }

    public String makeAccountNr11TestProof(String accountNr){
        String newAccountNr = accountNr;
        while(!passed11Test(newAccountNr)){
           newAccountNr = incrementAccountNrByOne(newAccountNr);
        }
        return newAccountNr;
    }

    public Account getAccountById(int accountId){

        Optional<Account> account =   accountRepository.findById(accountId);
        if(account.isPresent()){
            return account.get();
        }else
            return null;
    }

    public List<Customer> getAccountHolders(Account myAccount) {
        return new ArrayList<>(myAccount.getAccountHolders());

    }

    public Optional<Integer> getAccountIdByAccountNumber(String accountNumber){
        return accountRepository.getAccountIdByAccountNumber(accountNumber);
    }

    public Account getAccountFromAccountNumber(String accountNumber) {
        Account myAccount = null;
        Optional<Account> account = accountRepository.getAccountByAccountNumber(accountNumber);
        if(account.isPresent()){
            myAccount = account.get();
        }

        return myAccount;
    }


    public List<Account> getAccountsFromUserId(int userId) {
        Customer customer = (Customer) userService.findByUserId(userId);

        return new ArrayList<>(customer.getAccount());
    }


}
