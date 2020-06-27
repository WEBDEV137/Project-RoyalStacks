package royalstacks.app.model.dataGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import royalstacks.app.model.dataGenerator.TransactionGenerator;
import royalstacks.app.model.repository.AccountRepository;
import royalstacks.app.model.repository.CustomerRepository;
import royalstacks.app.model.repository.TransactionRepository;
import royalstacks.app.service.AccountService;

@Component
public class CommandRunner implements CommandLineRunner {

    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionGenerator transactionGenerator;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    Generator generator;

    public CommandRunner() {
    }

    @Override
    public void run(String... args) throws Exception {

       //stransactionRepository.deleteAll();
       generator.fillDbAllData();


    }
}

