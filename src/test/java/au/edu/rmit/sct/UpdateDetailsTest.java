package au.edu.rmit.sct;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

public class UpdateDetailsTest {

    private static final String TEST_FILE = "test_persons.txt";

    @BeforeEach
    public void setup() throws IOException {
        Files.write(Paths.get(TEST_FILE), String.join("\n",
            "3xy%z#78AA,John,Doe,12|Main Rd|Geelong|Victoria|Australia,01-01-2010",
            "5xy%z#78AA,Jane,Smith,20|Old Rd|Melbourne|Victoria|Australia,01-01-2000",
            "7ab@!Xz81P,Lucas,Green,14|Oak Ave|Geelong|Victoria|Australia,01-01-1995",
            "2ab#xZ@98L,Mia,Lee,77|Hill St|Melbourne|Victoria|Australia,01-01-1990"
        ).getBytes());
    }

    @Test
    public void testUnderageCannotChangeAddress() {
        updateDetails p = new updateDetails("3xy%z#78AA", "John", "Doe", "12|Main Rd|Geelong|Victoria|Australia", "01-01-2010");
        assertFalse(p.updatePersonalDetails("3xy%z#78AA", "John", "Doe", "99|New Rd|Geelong|Victoria|Australia", "01-01-2010", TEST_FILE));
    }

    @Test
    public void testAdultCanChangeAddress() {
        updateDetails p = new updateDetails("5xy%z#78AA", "Jane", "Smith", "20|Old Rd|Melbourne|Victoria|Australia", "01-01-2000");
        assertTrue(p.updatePersonalDetails("5xy%z#78AA", "Jane", "Smith", "88|New Rd|Melbourne|Victoria|Australia", "01-01-2000", TEST_FILE));
    }

    @Test
    public void testOnlyBirthdateIsChangedSuccessfully() {
        updateDetails p = new updateDetails("5xy%z#78AA", "Jane", "Smith", "88|New Rd|Melbourne|Victoria|Australia", "01-01-2000");
        assertTrue(p.updatePersonalDetails("5xy%z#78AA", "Jane", "Smith", "88|New Rd|Melbourne|Victoria|Australia", "01-01-2001", TEST_FILE));
    }

    @Test
    public void testBirthdateAndNameChangeShouldFail() {
        updateDetails p = new updateDetails("7ab@!Xz81P", "Lucas", "Green", "14|Oak Ave|Geelong|Victoria|Australia", "01-01-1995");
        assertFalse(p.updatePersonalDetails("7ab@!Xz81P", "Luke", "Green", "14|Oak Ave|Geelong|Victoria|Australia", "01-01-1996", TEST_FILE));
    }

    @Test
    public void testEvenStartingIDCannotBeChanged() {
        updateDetails p = new updateDetails("2ab#xZ@98L", "Mia", "Lee", "77|Hill St|Melbourne|Victoria|Australia", "01-01-1990");
        assertFalse(p.updatePersonalDetails("9ab#xZ@98L", "Mia", "Lee", "77|Hill St|Melbourne|Victoria|Australia", "01-01-1990", TEST_FILE));
    }


}