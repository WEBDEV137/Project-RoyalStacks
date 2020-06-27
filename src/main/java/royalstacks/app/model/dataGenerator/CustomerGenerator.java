package royalstacks.app.model.dataGenerator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import royalstacks.app.model.Customer;
import royalstacks.app.model.CustomerAddress;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerGenerator extends Gen {

    public static List<Customer> generateCustomers(JSONArray jsonArray){
        System.out.println("*****" + jsonArray.toString());
        final String PASSWORD = "Auto!12345";
        Iterator<JSONObject> iterator = jsonArray.iterator();
        List<Customer> customers = new ArrayList<>();

        while(iterator.hasNext()) {
            JSONObject person = iterator.next();
            String username = person.get("username").toString();
            System.out.println(username);
            String firstName = person.get("firstName").toString();
            String lastName = person.get("lastName").toString();
            String email = person.get("email").toString();
            String houseNumber = randomHousNr();
            String city = person.get("city").toString();
            String street = person.get("street").toString();
            String phoneNumber = person.get("phoneNumber").toString();
            String suffix = randomSuffix();
            String postcode = randomPostalCode();
            String bsn = randomBsn();
            CustomerAddress address = new CustomerAddress(postcode, houseNumber, suffix, city, street);
            Customer customer = new Customer(username, PASSWORD, firstName, lastName, email, address, phoneNumber, bsn, null,  false);
            customers.add(customer);
        }
        return customers;
    }
    public static String randomPostalCode(){
        final int MAX = 9999;
        final int MIN = 1000;
        final char MAX_CHAR = 'H';
        int number = Gen.randomInt(MIN, MAX);
        char letter1 =  Gen.randomChar(MAX_CHAR);
        char letter2 = Gen.randomChar(MAX_CHAR);
        return String.format("%d%c%c", number, letter1, letter2);
    }
    public static String randomBsn(){
        final int MAX =999999999;
        final int MIN = 1;
        int bsn = Gen.randomInt(MIN, MAX);
        return String.format("%09d", bsn);
    }

    public static String randomSuffix(){
        final int PERCENTAGE_NO_SUFFIX = 60;
        final int PERCENTAGE_LETTER_SUFFIX = 70;
        final char MAX_CHAR_SUFFIX = 'H';
        final int MIN_NUMBER_SUFFIX = 1;
        final int MAX_NUMBER_SUFFIX = 3;
        if(Gen.generateRandomTrueFalse(PERCENTAGE_NO_SUFFIX)){
           return "";
        }
        else if (Gen.generateRandomTrueFalse(PERCENTAGE_LETTER_SUFFIX)) {
            return String.valueOf(Gen.randomChar(MAX_CHAR_SUFFIX));
        }
        else return String.valueOf(Gen.randomInt(MIN_NUMBER_SUFFIX, MAX_NUMBER_SUFFIX));
    }
    public static String randomHousNr(){
        final int PERCENTAGE_UNDER_100 = 50;
        final int MIN_HOUSENR = 1;
        final int MAX_HOUSENR_COMMON = 100;
        final int MAX_HOUSENR_UNCOMMON = 1000;
        if(Gen.generateRandomTrueFalse(PERCENTAGE_UNDER_100)){
            return String.valueOf(Gen.randomInt(MIN_HOUSENR, MAX_HOUSENR_COMMON));
        }
        else return String.valueOf(Gen.randomInt(MAX_HOUSENR_COMMON, MAX_HOUSENR_UNCOMMON));

    }




}
