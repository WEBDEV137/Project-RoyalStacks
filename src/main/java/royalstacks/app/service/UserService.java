package royalstacks.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import royalstacks.app.model.Customer;
import royalstacks.app.model.Employee;
import royalstacks.app.model.User;
import royalstacks.app.model.repository.UserRepository;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 10;
    private static final int MAX_PASSWORD_LENGTH = 100;

    // username mag kleine letters, grote letters, getallen, en - of _ bevatten en moet tussen 3 en 20 characters lang zijn.
    private static final Pattern USERNAME_REGEX = Pattern.compile("^[a-zA-Z0-9_-]+$");
    // Moet 1 kleine letter, 1 grote letter, 1 nummer, 1 speciaal karakter en minstens 10 karakters lang zijn
    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!\"#$%&'()*+,\\-./:;<=>?@^_`{|}~\\[\\]])[A-Za-z\\d!\"#$%&'()*+,\\-./:;<=>?@^_`{|}~\\[\\]]+$");
    private static final Pattern NAME_REGEX = Pattern.compile("^([-a-zA-Z\\-']+(\\s+[-a-zA-Z\\-']+)*){2,100}$");

    @Qualifier("userRepository")
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository ur) {
        this.userRepository = ur;
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByUserId(int userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        else {
            return null;
        }
    }


    public boolean isUsernameFormatValid(String username){
            Matcher m = USERNAME_REGEX.matcher(username);
        return m.matches()
                && username.length() >= MIN_USERNAME_LENGTH
                && username.length() <= MAX_USERNAME_LENGTH;
    }

    public boolean isPasswordValid(String password){
        Matcher m = PASSWORD_REGEX.matcher(password);
        return m.matches()
                && password.length() >= MIN_PASSWORD_LENGTH
                && password.length() <= MAX_PASSWORD_LENGTH;
    }

    public boolean isNameValid(String name){
        Matcher m = NAME_REGEX.matcher(name);
        return m.matches();
    }

    public boolean isUserCustomer (User user){
        return (user instanceof Customer);
    }


}
