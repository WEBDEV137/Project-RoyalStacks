package royalstacks.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import royalstacks.app.model.*;
import royalstacks.app.model.repository.BusinessAccountRepository;
import royalstacks.app.model.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BusinessAccountService {

    @Autowired
    private BusinessAccountRepository businessAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<SectorAndAverageBalance> findSectorAndAverageBalance() {
        List<Object[]> results = businessAccountRepository.findSectorAndAverageBalance();
        List<SectorAndAverageBalance> sectorAndAverageBalances = new ArrayList<>();

        for (Object[] result : results) {
            sectorAndAverageBalances.add(
                    new SectorAndAverageBalance(
                            Sector.valueOf((String) result[0]),
                            (double) result[1]
                    )
            );
        }
        return sectorAndAverageBalances;

    }

    public Optional<BusinessAccount> findBusinessAccountByAccountNumber(String accountNumber){
        return businessAccountRepository.findBusinessAccountByAccountNumber(accountNumber);
    }


    public Optional<BusinessAccount> findByAccountId(int accountId) {
        return businessAccountRepository.findByAccountId(accountId);
    }

    public List<CompanyAndTransactions> findTop10TransactionsOnBusinessAccounts() {
        //List with objects that represent companies and business accounts
        List<Object[]> results = businessAccountRepository.findCompaniesAndBusinessAccounts();
        List<CompanyAndTransactions> companiesAndTransactionsList = enrichWithNumberOfTransactions(results);
        companiesAndTransactionsList = groupOnKvKNumber(companiesAndTransactionsList);

        //Sort list by number of transactions
        Collections.sort(companiesAndTransactionsList);

        //Return top10 results (or less if the resultlist is smaller than 10)
        return companiesAndTransactionsList.subList(0, Math.min(companiesAndTransactionsList.size(), 10));
    }

    private List<CompanyAndTransactions> groupOnKvKNumber(List<CompanyAndTransactions> companiesAndTransactionsList) {
        //Group list on kvkNumber
        HashMap<String, CompanyAndTransactions> hmap = new HashMap<>();
        for (CompanyAndTransactions companyAndTransactions : companiesAndTransactionsList) {
            if (hmap.containsKey(companyAndTransactions.getKvkNumber())) {
                CompanyAndTransactions existingValue = hmap.get(companyAndTransactions.getKvkNumber());
                existingValue.setBalance(existingValue.getBalance().add(companyAndTransactions.getBalance()));
                existingValue.setNumberOfTransactions(existingValue.getNumberOfTransactions() + companyAndTransactions.getNumberOfTransactions());
                hmap.put(companyAndTransactions.getKvkNumber(), existingValue);
            } else {
                hmap.put(companyAndTransactions.getKvkNumber(), companyAndTransactions);
            }
        }

        //Put hashmap grouped values back in list
        companiesAndTransactionsList = new ArrayList<>(hmap.values());
        return companiesAndTransactionsList;
    }

    private List<CompanyAndTransactions> enrichWithNumberOfTransactions(List<Object[]> results) {
        //Arraylist with companies, their business accounts and the transactions on those accounts
        List<CompanyAndTransactions> companiesAndTransactionsList = new ArrayList<>();

        //Enrich with number of transactions & join with object model
        for (Object[] result : results) {
            int numberOfTransactions = transactionRepository
                    .countByFromAccountIdOrToAccountId((int) result[0], (int) result[0]);

            companiesAndTransactionsList.add(
                    new CompanyAndTransactions(
                            (String) result[3],
                            (String) result[2],
                            numberOfTransactions,
                            (BigDecimal) result[1]
                    )
            );
        }
        return companiesAndTransactionsList;
    }
}
