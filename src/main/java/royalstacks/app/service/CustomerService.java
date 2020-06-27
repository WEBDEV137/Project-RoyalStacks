package royalstacks.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import royalstacks.app.model.Customer;
import royalstacks.app.model.CustomerAndTotalBalance;
import royalstacks.app.model.repository.CustomerRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomerService {

    private static final int BUSINESS_ACCOUNT_SIZE = 10;
    private static final int PRIVATE_ACCOUNT_SIZE = 10;
    private static final int BSN_LENGTH = 9;

    private static final Pattern PHONE_HOME_REGEX = Pattern.compile("^(((0)[1-9]{2}[0-9][-]?[1-9][0-9]{5})|((\\+31|0|0031)[1-9][0-9][-]?[1-9][0-9]{6}))$");
    private static final Pattern PHONE_MOBILE_REGEX = Pattern.compile("^(((\\\\+31|0|0031)6)[19][0-9]{7})$");
    private static final Pattern CITY_REGEX = Pattern.compile("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$");
    private static final Pattern HOUSENUMBER_REGEX = Pattern.compile("^[0-9]{1,6}+([-]\\d{1,5})?$");
    private static final Pattern POSTALCODE_REGEX = Pattern.compile("^[1-9][0-9]{3} ?(?!SA|SD|SS)[A-Z]{2}$");
    private static final Pattern BSN_REGEX = Pattern.compile("^[0-9]+$");
    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:" +
                    "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\" +
                    "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+" +
                    "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                    "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
                    "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserService userService;

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Optional<Customer> findCustomerByUserId(int userId){
        return customerRepository.findCustomerByUserId(userId);
    }

    public Optional<Customer> findCustomerByBSN(String BSN) {
        return customerRepository.findCustomerByBSN(BSN);
    }

    public List<CustomerAndTotalBalance> findTop10BusinessAccounts() {
        Pageable pageable = PageRequest.of(0, BUSINESS_ACCOUNT_SIZE);
        List<Object[]> results = customerRepository.findCustomersAndBusinessAccountBalance(pageable);
        List<CustomerAndTotalBalance> customersAndTotalBalance = new ArrayList<>();

        for (Object[] result : results) {
            customersAndTotalBalance.add(
                    new CustomerAndTotalBalance(
                            (String) result[0],
                            (String) result[1],
                            (BigDecimal) result[2]
                    )
            );
        }

        return customersAndTotalBalance;
    }

    public boolean isPhoneNumberValid(String phoneNumber){
        Matcher home = PHONE_HOME_REGEX.matcher(phoneNumber);
        Matcher mobile = PHONE_MOBILE_REGEX.matcher(phoneNumber);
        return  home.matches() || mobile.matches();
    }

    public boolean isCityValid(String city){
        Matcher m = CITY_REGEX.matcher(city);
        return m.matches();
    }

    public boolean isEmailValid(String email){
        Matcher m = EMAIL_REGEX.matcher(email);
        return m.matches();
    }

    public boolean isHouseNumberValid(String houseNumber){
        Matcher m = HOUSENUMBER_REGEX.matcher(houseNumber);
        return m.matches();
    }

    public boolean isPostalCodeValid(String postalCode) {
        postalCode = postalCode.replace(" ", "").toUpperCase();
        Matcher m = POSTALCODE_REGEX.matcher(postalCode);
        return m.matches();
    }

    public boolean isBSNFormatValid(String bsn){
        Matcher m = BSN_REGEX.matcher(bsn);
        if(!m.matches() || bsn.length() != BSN_LENGTH){
            return false;
        } else {
            // voer 11 proef voor BSN uit
            String firstNumbers = bsn.substring(0,BSN_LENGTH - 1);
            int lastNumber = Integer.parseInt(bsn.substring(BSN_LENGTH - 1)) * -1;

            int sum = 0;
            for (int i = 0; i < firstNumbers.length(); i++) {
                sum += firstNumbers.charAt(i) * (bsn.length() - i);
            }
            sum += lastNumber;

            return sum%11 == 0;
        }
    }

    public boolean isAllInputValid(Customer customer) {
        return userService.isUsernameFormatValid(customer.getUsername()) &&
                userService.findByUsername(customer.getUsername()).isEmpty() &&
                userService.isPasswordValid(customer.getPassword()) &&
                userService.isNameValid(customer.getFirstName()) &&
                userService.isNameValid(customer.getLastName()) &&
                isEmailValid(customer.getEmail()) &&

                isPhoneNumberValid(customer.getPhoneNumber()) &&
                isBSNFormatValid(customer.getBSN());

    }

    public List<CustomerAndTotalBalance> findTop10PrivateAccounts() {
        Pageable pageable = PageRequest.of(0, PRIVATE_ACCOUNT_SIZE);
        List<Object[]> results = customerRepository.findCustomersAndPrivateAccountBalance(pageable);
        List<CustomerAndTotalBalance> customersAndTotalBalance = new ArrayList<>();

        for (Object[] result : results) {
            customersAndTotalBalance.add(
                    new CustomerAndTotalBalance(
                            (String) result[0], // FIRSTNAME
                            (String) result[1], // LASTNAME
                            (BigDecimal) result[2] // BALANCE
                    )
            );
        }

        return customersAndTotalBalance;
    }


}
