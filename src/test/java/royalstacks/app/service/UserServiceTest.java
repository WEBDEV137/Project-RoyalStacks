package royalstacks.app.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Wesley Wong
 */

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void isUsernameFormatValid() {
        assertTrue(userService.isUsernameFormatValid("PietHein"));
        assertTrue(userService.isUsernameFormatValid("Piet"));
        assertTrue(userService.isUsernameFormatValid("KlaasJan88"));
        assertTrue(userService.isUsernameFormatValid("12345678901234567890"));
        assertTrue(userService.isUsernameFormatValid("Aart"));
        assertTrue(userService.isUsernameFormatValid("aerT"));
        assertTrue(userService.isUsernameFormatValid("Aad"));
        assertTrue(userService.isUsernameFormatValid("FietsP0mp"));

        assertFalse(userService.isUsernameFormatValid(" Piet Hein"));
        assertFalse(userService.isUsernameFormatValid("A"));
        assertFalse(userService.isUsernameFormatValid("Ap"));
        assertFalse(userService.isUsernameFormatValid("A1"));
        assertFalse(userService.isUsernameFormatValid("DitIsEenUsernameTooch"));
        assertFalse(userService.isUsernameFormatValid("123456789012345678901"));
        assertFalse(userService.isUsernameFormatValid("PietJan!"));
        assertFalse(userService.isUsernameFormatValid("!KlaasJan"));

    }

    @Test
    void isPasswordValid() {
        assertTrue(userService.isPasswordValid("aAbditDoti!2"));
        assertTrue(userService.isPasswordValid("!gggggggG2"));
        assertTrue(userService.isPasswordValid("!gggggggG2123456789012"));
        assertTrue(userService.isPasswordValid("123KlaasJan123!"));
        assertTrue(userService.isPasswordValid("!@#$%^&*()1234567Abd"));
        assertTrue(userService.isPasswordValid("!@#$%asdfasdf3@42!37Abd"));
        assertTrue(userService.isPasswordValid("dfafg34567!Abd"));
        assertTrue(userService.isPasswordValid("3423agdiE&&3"));

        assertFalse(userService.isPasswordValid("123Abcde!"));
        assertFalse(userService.isPasswordValid("abcefgij123!"));
        assertFalse(userService.isPasswordValid("JAJAJAJAJA155&^"));
        assertFalse(userService.isPasswordValid("JAJNENENEJA15"));
        assertFalse(userService.isPasswordValid("JAJ AJAJAJA15"));
        assertFalse(userService.isPasswordValid("geaoeifjdBdfe32"));
        assertFalse(userService.isPasswordValid("32142329ad!"));
        assertFalse(userService.isPasswordValid("BADFERadfsf23"));
    }

    @Test
    void isNameValid() {
        assertTrue(userService.isNameValid("Klaas-Jan"));
        assertTrue(userService.isNameValid("Klaas Jan"));
        assertTrue(userService.isNameValid("KlaasJan"));
        assertTrue(userService.isNameValid("Klaas"));
        assertTrue(userService.isNameValid("'t Veld"));
        assertTrue(userService.isNameValid("Ad"));
        assertTrue(userService.isNameValid("Wil-Jannie"));
        assertTrue(userService.isNameValid("Mostert-Uit de fles"));


        assertFalse(userService.isNameValid("Klaas99"));
        assertFalse(userService.isNameValid("Klaas*"));
        assertFalse(userService.isNameValid("Klaas!!"));
        assertFalse(userService.isNameValid(" Klaas"));
        assertFalse(userService.isNameValid("Klaas?"));
        assertFalse(userService.isNameValid("Klaas "));
        assertFalse(userService.isNameValid("1"));
        assertFalse(userService.isNameValid(""));

    }
}