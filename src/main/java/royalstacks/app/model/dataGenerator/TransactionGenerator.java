package royalstacks.app.model.dataGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import royalstacks.app.model.Account;
import royalstacks.app.model.Transaction;
import royalstacks.app.model.repository.AccountRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TransactionGenerator {

    @Autowired
    private AccountRepository accountRepository;

    public TransactionGenerator() {
    }

    public List<Transaction> generateTransactions(int amount){
        List<Account>  allAccounts = accountRepository.findAll();
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            int randomIndex = Gen.randomInt(0, allAccounts.size()-1);
            int randomIndex2 = Gen.randomInt(0, allAccounts.size()-1);
            while (randomIndex == randomIndex2){
                randomIndex2 = Gen.randomInt(0, allAccounts.size()-1);
            }
            int fromAccountId = allAccounts.get(randomIndex).getAccountId();
            int toAccountId = allAccounts.get(randomIndex2).getAccountId();
            Transaction transaction =
                    new Transaction(fromAccountId, toAccountId,randomTransferAmount() ,randomDescription(), randomDateTime());
            transactions.add(transaction);
        }
        return transactions;
    }
    private static BigDecimal randomTransferAmount(){
        final int MIN = 1;
        final int MAX_UNCOMMON = 10000;
        final int MAX_COMMON = 200;
        final int PERCENTAGE_COMMON = 85;
        return Gen.partiallyRandomAmount(MIN, MAX_COMMON, MAX_UNCOMMON, PERCENTAGE_COMMON);
    }
    private static LocalDateTime randomDateTime(){
        LocalDateTime start = LocalDateTime.of(2020, Month.FEBRUARY, 1, 9, 0);
        long seconds = ChronoUnit.SECONDS.between(start, LocalDateTime.now());
        return start.plusSeconds(new Random().nextInt((int)seconds+1));
    }

    private static String randomDescription(){
        StringBuilder description = new StringBuilder();
        final int MIN_LETTERS = 2;
        final int MAX_LETTERS = 7;
        final int MIN_WORDS = 0;
        final int MAX_WORDS = 7;
        int letterAmount = Gen.randomInt(MIN_LETTERS, MAX_LETTERS);
        int wordAmount = Gen.randomInt(MIN_WORDS, MAX_WORDS);

        for (int index = 0; index < wordAmount; index++) {
            String word = randomWord(letterAmount) + " ";
            description.append(word.toLowerCase());
        }
        return description.toString();
    }

    private static String randomWord(int letterAmount) {
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < letterAmount; i++) {
            char letter = Gen.randomChar('Z');
            word.append(letter);
        }
        return word.toString();
    }


}
