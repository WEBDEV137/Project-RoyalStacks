package royalstacks.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import royalstacks.app.model.*;
import royalstacks.app.model.PrivateAccount;
import royalstacks.app.model.repository.AccountRepository;
import royalstacks.app.model.repository.CustomerRepository;
import royalstacks.app.model.repository.EmployeeRepository;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Service
public class RecordGenerator {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;


    public void generateAllRecords(int amountCustomers, int amountEmployees, int amountAccounts){
        customerGenerator(amountCustomers);
        headBusinessGenerator(amountEmployees);
        headPrivateGenerator(amountEmployees);
        privateAccountGenerator(amountAccounts);
        businessAccountGenerator(amountAccounts);
       //addAccountHoldersToAllPrivatAccounts();
        addAccountHoldersToAllAccounts();



}
    public void customerGenerator(int amount){

        String usernameBase = "customer";
        String password = "Auto!12345";
        String name = "Piet";
        String lastname = "Boot";
        String emailAddress = "email@test.nl";
        String houseNumber = "22";
        String suffix = "a";
        String city = "Ede";
        String street = "Duindoornstraat";
        String postalCode = "1337GG";
        String phone = "0656475656";
        String socialSecurityNumber = "199887878";

        for (int index = 1; index <= amount ; index++) {
            String username = usernameBase + index;
            socialSecurityNumber = String.valueOf(Integer.parseInt(socialSecurityNumber)+1);
            CustomerAddress customerAddress = new CustomerAddress (postalCode, houseNumber, suffix, city, street);
            Customer customer = new Customer(username, password, name, lastname, emailAddress, customerAddress, phone, socialSecurityNumber, null, false);

            // only save this user if username does not exist yet
            if(userService.findByUsername(username).isEmpty()) {
                customerRepository.save(customer);
            }
        }
    }
    public void headBusinessGenerator(int amount) {
        String username = "headbusiness";
        String password = "Auto!12345";
        String name = "Piet";
        String lastname = "Boot";
        String position = "headbusiness";

        for (int index = 1; index <= amount; index++) {
            username = username + index;
            Employee employee = new Employee(username, password, name, lastname, position);
            // only save this user if username does not exist yet
            if(userService.findByUsername(username).isEmpty()) {
                employeeRepository.save(employee);
            }
        }
    }
    public void headPrivateGenerator(int amount) {
        String usernameBase = "headprivate";
        String password = "Auto!12345";
        String name = "Piet";
        String lastname = "Boot";
        String position = "headprivate";

        for (int index = 1; index <= amount; index++) {
            String username = usernameBase + index;
            Employee employee = new Employee(username, password, name, lastname, position);
            // only save this user if username does not exist yet
            if(userService.findByUsername(username).isEmpty()) {
                employeeRepository.save(employee);
            }
        }
    }
    public void privateAccountGenerator(int amount){
        for (int index = 0; index < amount; index++) {
            PrivateAccount privateAccount = new PrivateAccount(accountService.createNewIban(), Account.getStartingBalance());
            accountRepository.save(privateAccount);
        }

    }

    public void businessAccountGenerator(int amount){

         String companyName = "company";
         String kvkNumber = "58999426";
         String vatNumber = "NL858805315B01";
         Sector sector = Sector.ICT_AND_MEDIA;

        for (int index = 0; index < amount; index++) {
            BusinessAccount businessAccount = new BusinessAccount(accountService.createNewIban(), Account.getStartingBalance(), companyName, kvkNumber, vatNumber, sector);
            accountRepository.save(businessAccount);
        }

    }
    public void addAccountHoldersToSingleAccount(Account account){
        long countCustomers = customerRepository.count();
        int randomCountAccounholders = (int)(Math.random()*3);
        for (int index = 0; index < randomCountAccounholders; index++) {
            int randomId = (int) ((Math.random() * countCustomers) + 1);
            Optional<Customer> customer = customerRepository.findById(randomId);
            if (customer.isPresent()) {

                // check of deze klant al accountholder is van deze rekening, zo niet, voeg toe:
                boolean customerIsAllreadyAccountHolder = false;
                Set<Customer> accountHoldersList = account.getAccountHolders();
                for (Customer accountHolder: accountHoldersList) {
                    if(customer.equals(accountHolder)){
                        customerIsAllreadyAccountHolder = true;
                        break;
                    }
                }
                if(! customerIsAllreadyAccountHolder){
                    account.getAccountHolders().add(customer.get());
                    if(account instanceof BusinessAccount){
                        if(!customer.get().isBusinessAccountHolder()){
                            customer.get().setBusinessAccountHolder(true);
                            customer.get().setAccountManager(employeeRepository.findAll().iterator().next());
                        }
                    }
                }
            }
        }
        accountRepository.save(account);

    }

    public void addAccountHoldersToAllAccounts(){
        Iterable<Account> iterable = accountRepository.findAll();
        Iterator<Account> accounts = iterable.iterator();
        while (accounts.hasNext()){
            Account account = accounts.next();
            addAccountHoldersToSingleAccount(account);
        }
    }
}
