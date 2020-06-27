package royalstacks.app.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BusinessAccountTest {

    @Test
    void isCompanyNameFormatValid() {
        BusinessAccount account = new BusinessAccount();

        // true: a-z
        account.setCompanyName("google");
        assertTrue(account.isCompanyNameFormatValid());

        // true: A-Z
        account.setCompanyName("GOOGLE");
        assertTrue(account.isCompanyNameFormatValid());

        // false: <1 char
        account.setCompanyName("");
        assertFalse(account.isCompanyNameFormatValid());

        // true: a-z string char
        account.setCompanyName("a");
        assertTrue(account.isCompanyNameFormatValid());

        // true: A-Z string char
        account.setCompanyName("A");
        assertTrue(account.isCompanyNameFormatValid());

        // true: number char
        account.setCompanyName("1");
        assertTrue(account.isCompanyNameFormatValid());

        // false: not allowed special char
        account.setCompanyName("*");
        assertFalse(account.isCompanyNameFormatValid());
        account.setCompanyName("<");
        assertFalse(account.isCompanyNameFormatValid());

        // false: a-z and not allowed special char
        account.setCompanyName("a*");
        assertFalse(account.isCompanyNameFormatValid());

        // false: only allowed special chars(s)
        account.setCompanyName("_");
        assertFalse(account.isCompanyNameFormatValid());
        account.setCompanyName("@");
        assertFalse(account.isCompanyNameFormatValid());
        account.setCompanyName("____");
        assertFalse(account.isCompanyNameFormatValid());

        // false: a-z and not allowed special char in between
        account.setCompanyName("google*co ");
        assertFalse(account.isCompanyNameFormatValid());

        // false: only special char and spaces
        account.setCompanyName(" _");
        assertFalse(account.isCompanyNameFormatValid());
        account.setCompanyName("_ ");
        assertFalse(account.isCompanyNameFormatValid());
        account.setCompanyName(" _ ");
        assertFalse(account.isCompanyNameFormatValid());

        // false: only spaces
        account.setCompanyName("   ");
        assertFalse(account.isCompanyNameFormatValid());

        // true: a-z and spaces
        account.setCompanyName("the one");
        assertTrue(account.isCompanyNameFormatValid());

        // true: a-z and allowed special char in between
        account.setCompanyName("g_g");
        assertTrue(account.isCompanyNameFormatValid());
        account.setCompanyName("google_go");
        assertTrue(account.isCompanyNameFormatValid());
        account.setCompanyName("google-go");
        assertTrue(account.isCompanyNameFormatValid());
        account.setCompanyName("Google-go");
        assertTrue(account.isCompanyNameFormatValid());
        account.setCompanyName("google@co ");
        assertTrue(account.isCompanyNameFormatValid());

        // true: a-z and allowed special char at end
        account.setCompanyName("g_");
        assertTrue(account.isCompanyNameFormatValid());
        account.setCompanyName("g-");
        assertTrue(account.isCompanyNameFormatValid());
        account.setCompanyName("g@");
        assertTrue(account.isCompanyNameFormatValid());
        account.setCompanyName("g&");
        assertTrue(account.isCompanyNameFormatValid());
        account.setCompanyName("g+");
        assertTrue(account.isCompanyNameFormatValid());
        account.setCompanyName("google co-");
        assertTrue(account.isCompanyNameFormatValid());
        account.setCompanyName("google co @");
        assertTrue(account.isCompanyNameFormatValid());


        // true: a-z and allowed special char at beginning
        account.setCompanyName("_g");
        assertTrue(account.isCompanyNameFormatValid());
        account.setCompanyName("@google");
        assertTrue(account.isCompanyNameFormatValid());
    }

    @Test
    void isKvkNameFormatValid() {

        BusinessAccount account = new BusinessAccount();
        //true: = 8 numbers
        account.setKvkNumber("12345678");
        assertTrue(account.isKvkNameFormatValid());

        // false: < 8 numbers
        account.setKvkNumber("1234567");
        assertFalse(account.isKvkNameFormatValid());

        // false: > 8 numbers
        account.setKvkNumber("123456789");
        assertFalse(account.isKvkNameFormatValid());

        // false: 8 a-z chars
        account.setKvkNumber("abcdefgh");
        assertFalse(account.isKvkNameFormatValid());

        // false: 8 spaces
        account.setKvkNumber("        ");
        assertFalse(account.isKvkNameFormatValid());

        // false: 8 special chars
        account.setKvkNumber("********");
        assertFalse(account.isKvkNameFormatValid());

        // false: empty string
        account.setKvkNumber("");
        assertFalse(account.isKvkNameFormatValid());

        // false: 8 chars of which 1 space
        account.setKvkNumber("1234 678");
        assertFalse(account.isKvkNameFormatValid());

        // false: >8 chars of which 1 space
        account.setKvkNumber("1234 5678");
        assertFalse(account.isKvkNameFormatValid());

        // false: 9 chars of which 1 special char
        account.setKvkNumber("12345678.");
        assertFalse(account.isKvkNameFormatValid());
    }

    @Test
    void isVatFormatValid(){
        BusinessAccount account = new BusinessAccount();

        //true: correct vat number
        account.setVatNumber("NL123456789B12");
        assertTrue(account.isVatFormatValid());

        // true: NL-variations NL / nL / Nl / nl
        account.setVatNumber("Nl123456789B12");
        assertTrue(account.isVatFormatValid());
        account.setVatNumber("nL123456789B12");
        assertTrue(account.isVatFormatValid());
        account.setVatNumber("nl123456789B12");
        assertTrue(account.isVatFormatValid());

        // true: B-variation
        account.setVatNumber("NL123456789b12");
        assertTrue(account.isVatFormatValid());

        // false: first char is not an N
        account.setVatNumber("AL123456789B12");
        assertFalse(account.isVatFormatValid());
        account.setVatNumber("L123456789B12");
        assertFalse(account.isVatFormatValid());
        account.setVatNumber("*L123456789B12");
        assertFalse(account.isVatFormatValid());
        account.setVatNumber(" L123456789B12");
        assertFalse(account.isVatFormatValid());

        // false: second char is nog an L
        account.setVatNumber("NA123456789B12");
        assertFalse(account.isVatFormatValid());
        account.setVatNumber("N123456789B12");
        assertFalse(account.isVatFormatValid());
        account.setVatNumber("N*123456789B12");
        assertFalse(account.isVatFormatValid());
        account.setVatNumber("N 123456789B12");
        assertFalse(account.isVatFormatValid());

        // false: numbers in first numberstring are not numbers
        account.setVatNumber("NL 12345678B12");
        assertFalse(account.isVatFormatValid());
        account.setVatNumber("NL12345678 B12");
        assertFalse(account.isVatFormatValid());
        account.setVatNumber("NL12345678XB12");
        assertFalse(account.isVatFormatValid());
        account.setVatNumber("NL1234X6789B12");
        assertFalse(account.isVatFormatValid());
        account.setVatNumber("NLX2345678 B12");
        assertFalse(account.isVatFormatValid());

        // false: <9 numbers in first numberstring
        account.setVatNumber("NL12345678B12");
        assertFalse(account.isVatFormatValid());

        // false: >9 numbers in first numberstring
        account.setVatNumber("NL1234567890B12");

        // false: third char is not a B
        account.setVatNumber("NL123456789A12");
        assertFalse(account.isVatFormatValid());
        account.setVatNumber("NL123456789a12");
        assertFalse(account.isVatFormatValid());
        account.setVatNumber("NL123456789*12");
        assertFalse(account.isVatFormatValid());
        account.setVatNumber("NL123456789 12");
        assertFalse(account.isVatFormatValid());
        account.setVatNumber("NL12345678912");
        assertFalse(account.isVatFormatValid());

        // false: < 2 numbers in last 2 chars
        account.setVatNumber("NL123456789B1");
        assertFalse(account.isVatFormatValid());

        // false: > 2 numbers in last 2 chars
        account.setVatNumber("NL123456789B123");
        assertFalse(account.isVatFormatValid());
    }

    @Test
    void vatPassed11Test() {
        BusinessAccount account = new BusinessAccount();

        //given that format used = correct (checked by isVatFormatValid:

        //true: correct vat number (9 = checksum)
        account.setVatNumber("NL316393915B12");
        assertTrue(account.vatPassed11Test());

        // false: wrong checksum-number (+1 till +9)
        account.setVatNumber("NL316303915B12");
        assertFalse(account.vatPassed11Test());

        account.setVatNumber("NL316313915B12");
        assertFalse(account.vatPassed11Test());

        account.setVatNumber("NL316323915B12");
        assertFalse(account.vatPassed11Test());

        account.setVatNumber("NL316333915B12");
        assertFalse(account.vatPassed11Test());

        account.setVatNumber("NL316343915B12");
        assertFalse(account.vatPassed11Test());

        account.setVatNumber("NL316353915B12");
        assertFalse(account.vatPassed11Test());

        account.setVatNumber("NL316363915B12");
        assertFalse(account.vatPassed11Test());

        account.setVatNumber("NL316373915B12");
        assertFalse(account.vatPassed11Test());

        account.setVatNumber("NL316383915B12");
        assertFalse(account.vatPassed11Test());
    }

    @Test
    void convertSectorEnumTest(){
        //ARRANGE
        BusinessAccount businessAccount = new BusinessAccount("NL316383915B12", new BigDecimal(666.66),"TestBV","12345678","NL010000446B01",Sector.ENERGY_WATER_AND_ENVIRONMENT);
        Sector expected = Sector.ENERGY_WATER_AND_ENVIRONMENT;

        //ACT
        Sector actual = businessAccount.getSector();

        //ASSERT
        assertEquals(expected,actual);
    }
}