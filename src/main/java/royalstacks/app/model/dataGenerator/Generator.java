package royalstacks.app.model.dataGenerator;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import royalstacks.app.model.*;
import royalstacks.app.model.repository.AccountRepository;
import royalstacks.app.model.repository.CustomerRepository;
import royalstacks.app.model.repository.EmployeeRepository;
import royalstacks.app.model.repository.TransactionRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Generator {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionGenerator transactionGenerator;

    private static final String customer_data_1 = "src/main/resources/customer_data_1.json";
    private static final String customer_data_2 = "src/main/resources/customer_data_2.json";
    private static final String customer_data_3 = "src/main/resources/customer_data_3.json";
    private static final String customer_data_4 = "src/main/resources/customer_data_4.json";
    private static final String companyName1 = "src/main/resources/companyName1.json";

    List<Customer> allCustomers;
    Employee headBusiness;
    Employee headPrivate;
    List<Account> allAccounts;

    public Generator() {
        allCustomers = new ArrayList<>();
        allAccounts = new ArrayList<>();
    }

    public void fillDbAllData() throws IOException {
      /* fillDbCustomers();
       fillDbHeadbusiness();
       fillDbHeadPrivate();
       fillDbAccounts();
       fillDbAccountholder();*/
       fillDbtransactions();

    }

    private void fillDbCustomers() throws IOException {
        fillDbCustomerBatch(customer_data_1);
        fillDbCustomerBatch(customer_data_2);
        fillDbCustomerBatch(customer_data_3);
        fillDbCustomerBatch(customer_data_4);
    }
    private void fillDbHeadbusiness(){
        headBusiness = EmployeeGenerator.headBusinessGenerator();
        employeeRepository.save(headBusiness);
    }
    private void fillDbHeadPrivate(){
        headPrivate = EmployeeGenerator.headPrivateGenerator();
        employeeRepository.save(headPrivate);
    }
    private void fillDbAccounts() {
        fillDbBusinessAccountBatch(companyName1);
        fillDbPrivateAccountBatch();
        fillDbPrivateAccountBatch();
        fillDbPrivateAccountBatch();
    }

    private void fillDbAccountholder(){
        AccountHolderAdder.addAccountHoldersToAccount(allAccounts, allCustomers, headBusiness);
        accountRepository.saveAll(allAccounts);
    }
    private  void fillDbtransactions(){
        List<Transaction> transactions;
        for (int i = 0; i < 20; i++) {
            transactions = transactionGenerator.generateTransactions(1000);
            transactionRepository.saveAll(transactions);
        }
    }
    private void fillDbCustomerBatch(String fileName){
        JSONArray customerJson = Gen.createJsonArrayFromFile(fileName);
        List<Customer> customers = CustomerGenerator.generateCustomers(customerJson);
        allCustomers.addAll(customers);
        customerRepository.saveAll(customers);
    }
    private void fillDbBusinessAccountBatch(String fileName){
        JSONArray companyJson = Gen.createJsonArrayFromFile(fileName);
        List<Account> businessAccounts = AccountGenerator.businessAccountGenerator(1000, companyJson);
        allAccounts.addAll(businessAccounts);
        accountRepository.saveAll(businessAccounts);
    }
    private void fillDbPrivateAccountBatch(){
        List<Account> privateAccounts = AccountGenerator.privateAccountGenerator(1000);
        allAccounts.addAll(privateAccounts);
        accountRepository.saveAll(privateAccounts);
    }
}
