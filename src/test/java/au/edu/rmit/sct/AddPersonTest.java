package au.edu.rmit.sct;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AddPersonTest {

    // --- Valid Inputs ---
    @Test
    public void testValidInput1() {
        Person p = new Person("29#%_rt&ZX", "John", "Doe", "12|Main Rd|Geelong|Victoria|Australia", "01-01-1985");
        assertTrue(p.addPerson());
    }

    @Test
    public void testValidInput2() {
        Person p = new Person("89x@!gt^QW", "Jane", "Smith", "100|Lygon St|Carlton|Victoria|Australia", "29-02-2020");
        assertTrue(p.addPerson());
    }

    @Test
    public void testValidInput3() {
        Person p = new Person("56s_d%&fAB", "Alice", "Wong", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertTrue(p.addPerson());
    }

    // --- Invalid PersonID ---
    @Test
    public void testMissingSpecialChars() {
        Person p = new Person("56sadcfgAB", "John", "Doe", "12|Main Rd|Geelong|Victoria|Australia", "01-01-1990");
        assertFalse(p.addPerson());
    }

    @Test
    public void testTooShortID() {
        Person p = new Person("56s_d%&fA", "John", "Doe", "12|Main Rd|Geelong|Victoria|Australia", "01-01-1990");
        assertFalse(p.addPerson());
    }

    @Test
    public void testEndsWithLowercase() {
        Person p = new Person("56s_d%&fab", "John", "Doe", "12|Main Rd|Geelong|Victoria|Australia", "01-01-1990");
        assertFalse(p.addPerson());
    }

    @Test
    public void testStartsWithNonDigit() {
        Person p = new Person("xy_d%&fab", "John", "Doe", "12|Main Rd|Geelong|Victoria|Australia", "01-01-1990");
        assertFalse(p.addPerson());
    }

    // --- Invalid Address Format ---
    @Test
    public void testAddressMissingPipes() {
        Person p = new Person("78_#a&gZXY", "Jane", "Smith", "32 Highland Street Melbourne Victoria Australia", "20-10-1985");
        assertFalse(p.addPerson());
    }

    @Test
    public void testWrongStateAbbreviation() {
        Person p = new Person("78_#a&gZXY", "Jane", "Smith", "12|Main Rd|Geelong|VIC|Australia", "20-10-1985");
        assertFalse(p.addPerson());
    }

    @Test
    public void testTooFewAddressParts() {
        Person p = new Person("67&!z#JKLO", "Mia", "Lee", "22|Queen St|Ballarat|Victoria", "11-09-1991");
        assertFalse(p.addPerson());
    }

    @Test
    public void testAddressWithCommas() {
        Person p = new Person("23^%$gTRWE", "Mia", "Lee", "77,Park Ave,Melbourne,Victoria,Australia", "08-12-2000");
        assertFalse(p.addPerson());
    }

    // --- Invalid Birthdate Format ---
    @Test
    public void testWrongDateFormat() {
        Person p = new Person("67&!z#JKLO", "Mia", "Lee", "22|Queen St|Ballarat|Victoria|Australia", "1990-11-15");
        assertFalse(p.addPerson());
    }

    @Test
    public void testWrongSeparatorInDate() {
        Person p = new Person("67&!z#JKLO", "Mia", "Lee", "22|Queen St|Ballarat|Victoria|Australia", "15/11/1990");
        assertFalse(p.addPerson());
    }

    @Test
    public void testInvalidCalendarDate() {
        Person p = new Person("67&!z#JKLO", "Mia", "Lee", "22|Queen St|Ballarat|Victoria|Australia", "31-02-2024");
        assertFalse(p.addPerson());
    }

    // --- Multiple Invalid Fields ---
    @Test
    public void testMultipleInvalidFields1() {
        Person p = new Person("12abcdAB", "Tim", "Lane", "123 Main St, Melbourne", "2023/02/30");
        assertFalse(p.addPerson());
    }

    @Test
    public void testMultipleInvalidFields2() {
        Person p = new Person("56sdfgAB", "Tim", "Lane", "12|King St|Sydney|NSW|Australia", "31-02-2020");
        assertFalse(p.addPerson());
    }

    @Test
    public void testMultipleInvalidFields3() {
        Person p = new Person("89x@^&QW", "Tim", "Lane", "99|View Rd|Ballarat|Victoria|Australia", "15-13-2000");
        assertFalse(p.addPerson());
    }
}