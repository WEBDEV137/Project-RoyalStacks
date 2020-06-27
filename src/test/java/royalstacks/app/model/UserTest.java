package royalstacks.app.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void checkCorrectPassword() {
        String test_password = "abcdefghijklmnopqrstuvwxyz";

        String computed_hash = User.hashPassword(test_password);

        Assert.assertTrue(User.checkPassword(test_password, computed_hash));
    }

    @Test
    void checkFalsePassword() {
        String test_password = "abcdefghijklmnopqrstuvwxyz";
        String test_hash = "$2a$06$.rCVZVOThsIa97pEXOxvGuRRgzG64bvtJ0938xuqzv18d3ZpQhstC";

        Assert.assertFalse(User.checkPassword(test_password, test_hash));
    }
}