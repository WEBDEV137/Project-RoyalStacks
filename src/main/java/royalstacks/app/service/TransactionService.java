package royalstacks.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import royalstacks.app.model.Account;
import royalstacks.app.model.AccountHolderTransaction;
import royalstacks.app.model.Transaction;
import royalstacks.app.model.repository.TransactionRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TransactionService {

    private static final Logger LOGGER = Logger.getLogger(TransactionService.class.getName());

    private AccountService accountService;
    private TransactionRepository transactionRepository;

    private Transaction transaction;
    private Account fromAccount;
    private Account toAccount;

    @Autowired
    public TransactionService(AccountService as, TransactionRepository tr){
        this.accountService = as;
        this.transactionRepository = tr;
    }

    public final Optional<Transaction> executeTransaction(Transaction t){
        setAttributes(t);

        if(isTransactionValid()) {
            updateBalances();
            saveTransaction();

            LOGGER.log(Level.INFO, "**** Transaction has been executed");
            return Optional.of(transaction);
        } else {
            LOGGER.log(Level.SEVERE,"**** Transaction FAILED");
            return Optional.ofNullable(null);
        }
    }

    private void setAttributes(Transaction t){
        this.transaction = t;
        this.fromAccount = accountService.getAccountById(transaction.getFromAccountId());
        this.toAccount = accountService.getAccountById(transaction.getToAccountId());
    }

    private boolean isTransactionValid(){
        return !this.fromAccount.equals(this.toAccount) || this.fromAccount.getBalance().compareTo(this.transaction.getAmount()) >= 0;
    }

    private void updateBalances(){
        this.fromAccount.subtractAmount(this.transaction.getAmount());
        accountService.saveAccount(this.fromAccount);

        this.toAccount.addAmount(this.transaction.getAmount());
        accountService.saveAccount(this.toAccount);
    }

    private void saveTransaction(){
        transactionRepository.save(this.transaction);
    }


    //CREATE LIST OF TRANSACTION WHICH FOR THE ACCOUNTDETAIL

    public List<AccountHolderTransaction> getTenLastTransaction(int accountId){
        List<AccountHolderTransaction> accountHoldersTransactions = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.getTransactionsByFromAccountIdOrToAccountIdOrderByDateDesc(accountId,accountId);
        List<Transaction> tenLastTransactions = new ArrayList<>();
        for(int index =0; index < 10 && index < transactions.size(); index++){
            tenLastTransactions.add(transactions.get(index));
        }

        for (Transaction transaction1 : tenLastTransactions) {
            if(transaction1 != null) {
                AccountHolderTransaction accountTransaction = getTransaction(transaction1, accountId);
                accountHoldersTransactions.add(accountTransaction);
            }
        }
        return  accountHoldersTransactions;

    }

    //GET ONE TRANSACTION

    public AccountHolderTransaction getTransaction(Transaction transaction, int accountId) {
        AccountHolderTransaction accountHolderTransaction = new AccountHolderTransaction();
        Account accountFrom;


        //IF ID ACCOUNT IS SAME AS ACCOUNTFROM, THEN THEN MONEY GETS DEBITET FROM ACCOUNT
        if(transaction.getFromAccountId() == accountId) {
            accountFrom = accountService.getAccountById(transaction.getToAccountId());
            accountHolderTransaction = fillTransactionWithCorrectValue(accountFrom,transaction);
            accountHolderTransaction.setDebit(transaction.getAmount());

            //IF ID ACCOUNT IS SAME AS ACCOUNT TO, THEN THEN MONEY GETS CREDITEN TO ACCOUNT
        }else if(transaction.getToAccountId() == accountId){
            accountFrom = accountService.getAccountById(transaction.getFromAccountId());
            accountHolderTransaction = fillTransactionWithCorrectValue(accountFrom,transaction);
            accountHolderTransaction.setCredit(transaction.getAmount());

        }
        return accountHolderTransaction;
    }

    // AND FILL THE TRANSCATION WITH CORRECT AND FORMATED VALUE
    public AccountHolderTransaction fillTransactionWithCorrectValue(Account accountFrom, Transaction transaction) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String datetime = transaction.getDate().format(formatter);
        String bankaccount = null;
        if(accountFrom != null) {
            bankaccount = accountFrom.getAccountNumber();
        }
        String description = transaction.getDescription();


        return new AccountHolderTransaction(datetime,bankaccount,description,null,null);
    }






}
