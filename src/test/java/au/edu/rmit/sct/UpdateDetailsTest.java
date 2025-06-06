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
            "2ab#xZ@98L,Mia,Lee,77|Hill St|Melbourne|Victoria|Australia,01-01-1990",
            "1sr$y@51AP,Sam,Karan,102|Plam St|Melbourne|Victoria|Australia,01-01-2000",
            "3mj$B!51QC,Isla,Patel,85|Queen St|Richmond|Victoria|Australia,15-09-2003",
            "9qw&t!99ER,Rishit,Patel,74|Castle Ave|Melbourne|Victoria|Australia,15-09-2003"

        ).getBytes());
    }

    @Test
    public void testUnderageCannotChangeAddress() {
        updateDetails p = new updateDetails("3xy%z#78AA", "John", "Doe", "12|Main Rd|Geelong|Victoria|Australia", "01-01-2010");
        assertFalse(p.updatePersonalDetails("3xy%z#78AA", "John", "Doe", "99|New Rd|Geelong|Victoria|Australia", "01-01-2010", TEST_FILE));
    }
    @Test
    public void testUnderageCannotChangeAddress1() {
        updateDetails p1 = new updateDetails("4ab%a#99SS", "Peter", "Warner", " 10|Elm St|RockBank|Victoria|Australia", "30-05-2013");
        assertFalse(p1.updatePersonalDetails("4ab%a#99SS", "Peter", "Warner", "77|Emslie Dr|Cranbourne|Victoria|Australia", "30-05-2013", TEST_FILE));
    }

    @Test
    public void testAdultCanChangeAddress() {
        updateDetails p2 = new updateDetails("5xy%z#78AA", "Jane", "Smith", "20|Old Rd|Melbourne|Victoria|Australia", "01-01-2000");
        assertTrue(p2.updatePersonalDetails("5xy%z#78AA", "Jane", "Smith", "88|New Rd|Melbourne|Victoria|Australia", "01-01-2000", TEST_FILE));
    }
    @Test
    public void testAdultCanChangeAddress2(){
       updateDetails p3 = new updateDetails("1sr$y@51AP","Sam", "Karan", "102|Plam St|Melbourne|Victoria|Australia","01-01-2000");
       assertTrue(p3.updatePersonalDetails("1sr$y@51AP ", "Sam", "Karan", "99|Germain St|Lynbrook|Victoria|Australia", "01-01-2000", TEST_FILE));
}

    @Test
    public void testOnlyBirthdateIsChangedSuccessfully() {
        updateDetails p4 = new updateDetails("9qw&t!99ER", "Rishit", "Patel", "74|Castle Ave|Melbourne|Victoria|Australia", "02-02-2001");
        assertTrue(p4.updatePersonalDetails("9qw&t!99ER", "Rishit", "Patel", "74|Castle Ave|Melbourne|Victoria|Australia", "15-09-2003", TEST_FILE));
    }

    @Test
    public void testOnlyBirthdateIsChangedSuccessfully1() {
        updateDetails p5 = new updateDetails("3mj$B!51QC","Isla","Patel","85|Queen St|Richmond|Victoria|Australia","23-06-1993");
        assertTrue(p5.updatePersonalDetails("3mj$B!51QC","Isla","Patel","85|Queen St|Richmond|Victoria|Australia","30-12-2004", TEST_FILE));
    }


    @Test
    public void testBirthdateAndNameChangeShouldFail() {
        updateDetails p6 = new updateDetails("7ab@!Xz81P", "Lucas", "Green", "14|Oak Ave|Geelong|Victoria|Australia", "01-01-1995");
        assertFalse(p6.updatePersonalDetails("7ab@!Xz81P", "Luke", "Green", "14|Oak Ave|Geelong|Victoria|Australia", "01-01-1996", TEST_FILE));
    }
    @Test
    public void testBirthdateAndAddressChangeShouldFail() {
        updateDetails p7 = new updateDetails("9pk#A@78XZ","Nathan","Lopez","12|Forest Rd|Dandenong|Victoria|Australia","12-12-2002");
        assertFalse(p7.updatePersonalDetails("9pk#A@78XZ","Nathan","Lopez","25|Mountain Dr|Noble Park|Victoria|Australia","12-12-2002", TEST_FILE));
    }
    @Test
    public void testEvenStartingIDCannotBeChanged() {
        updateDetails p8 = new updateDetails("2ab#xZ@98L", "Mia", "Lee", "77|Hill St|Melbourne|Victoria|Australia", "01-01-1990");
        assertFalse(p8.updatePersonalDetails("9ab#xZ@98L", "Mia", "Lee", "77|Hill St|Melbourne|Victoria|Australia", "01-01-1990", TEST_FILE));
    }
    @Test
    public void testEvenStartingIDCannotBeChanged2() {
        updateDetails p9 = new updateDetails("2mn*W@96LB","Liam","Chen","99|Station Rd|Werribee|Victoria|Australia","04-04-1990");
        assertFalse(p9.updatePersonalDetails("5mn*W@96LB","Liam","Chen","99|Station Rd|Werribee|Victoria|Australia","04-04-1990", TEST_FILE));
    }    


}