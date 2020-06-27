package royalstacks.app.model.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import royalstacks.app.model.Customer;
import royalstacks.app.model.CustomerAddress;
import royalstacks.app.model.Employee;
import royalstacks.app.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByUsername() {

        CustomerAddress customerAddress = new CustomerAddress("postalCode", "houseNumber", "suffix", "city", "street");
        Customer customer = new Customer("username","password",  "firstName", "LastName", "email", customerAddress, "phonenumber","BSN", null, false);
        this.entityManager.persist(customer);
        Optional<User> expected = Optional.of(customer);

        Optional<User> actual = userRepository.findByUsername("username");

        assertThat(actual).isEqualTo(expected);
    }
}
