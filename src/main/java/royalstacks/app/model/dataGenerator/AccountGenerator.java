package royalstacks.app.model.dataGenerator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import royalstacks.app.model.*;
import royalstacks.app.service.AccountService;
import royalstacks.app.model.dataGenerator.Gen;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountGenerator {

    static String lastAccountNr = "0000000000";

    public static List<Account> businessAccountGenerator(int amount, JSONArray jsonArray) {
        Iterator<JSONObject> iterator = jsonArray.iterator();
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < amount ; i++) {
            if(iterator.hasNext()) {
                JSONObject company = iterator.next();
                String companyName = company.get("companyName").toString();
                String kvkNumber = randomKvkNumber();
                String vatNumber = randomVatNumber();
                Sector sector = randomSector();
                BigDecimal balance = randomBalance();
                String iban = nextIban(lastAccountNr);
                lastAccountNr = iban.substring(8);
                Account businessAccount = new BusinessAccount(iban, balance, companyName, kvkNumber, vatNumber, sector);
                accounts.add(businessAccount);
            }
        }return accounts;
    }
    public static List<Account> privateAccountGenerator(int amount){
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            String iban = nextIban(lastAccountNr);
            lastAccountNr = iban.substring(8);
            Account privateAccount = new PrivateAccount(iban, randomBalance());

            System.out.println(privateAccount);
            accounts.add(privateAccount);
        }return accounts;
    }
    private static String randomKvkNumber(){
        final int KVK_MIN = 1;
        final int KVK_MAX = 99999999;
        int kvkNumber = Gen.randomInt(KVK_MIN, KVK_MAX);
        return String.format("%08d", kvkNumber);
    }
    private static String randomVatNumber(){
        final String COUNTRY_CODE = "NL";
        final char VAT_LETTER = 'B';
        final int MIN_VAT = 1;
        final int MAX_START_NUMBER = 99999999;
        final int MAX_END_NUMBER = 99;
        int startNumber = Gen.randomInt(MIN_VAT, MAX_START_NUMBER);
        int endNumber = Gen.randomInt(MIN_VAT, MAX_END_NUMBER);
        return String.format("%s%08d%c%02d", COUNTRY_CODE, startNumber, VAT_LETTER, endNumber);
    }
    private static Sector randomSector(){
        Sector[] sectors = Sector.values();
        return (Sector)Array.get(sectors,Gen.randomInt(0, sectors.length-1));
    }
    private static BigDecimal randomBalance(){
        final int MIN = 0;
        final int MAX_UNCOMMON = 10000000;
        final int MAX_COMMON = 5000;
        final int PERCENTAGE_COMMON = 85;
        return Gen.partiallyRandomAmount(MIN, MAX_COMMON, MAX_UNCOMMON, PERCENTAGE_COMMON);
    }
    private static String nextIban(String previousAccountNr){
        final String LANDCODE = "NL";
        final String BANKCODE = "ROYA";
        AccountService accountService = new AccountService();
        String accountNr = accountService.incrementAccountNrByOne(previousAccountNr);
        accountNr = accountService.makeAccountNr11TestProof(accountNr);
        String controlNr = accountService.createControlNrFromAccountNr(accountNr);
        return  LANDCODE + controlNr + BANKCODE + accountNr;


    }

}
