package au.edu.rmit.sct;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UpdateDetailsTest {

    @Test
    //under 18 age user cannot change the address
    public void testUnderageCannotChangeAddress() {
        updateDetails p = new updateDetails("3xy%z#78AA", "John", "Doe", "12|Main Rd|Geelong|Victoria|Australia", "01-01-2010");
        assertFalse(p.updatePersonalDetails("3xy%z#78AA", "John", "Doe", "99|New Rd|Geelong|Victoria|Australia", "01-01-2010"));
    }
    @Test
    //under 18 age user cannot change the address
    public void testUnderageCannotChangeAddress1() {
        updateDetails p1 = new updateDetails("4ab%a#99SS", "Peter", "Warner", " 10|Elm St|RockBank|Victoria|Australia", "30-05-2013");
        assertFalse(p1.updatePersonalDetails("4ab%a#99SS", "Peter", "Warner", "77|Emslie Dr|Cranbourne|Victoria|Australia", "30-05-2013"));
    }

    @Test
    //exactly 18 year user can change the address
    public void testAdultCanChangeAddress() {
        updateDetails p2 = new updateDetails("5xy%z#78AA", "Jane", "Smith", "20|Old Rd|Melbourne|Victoria|Australia", "01-01-2007");
        assertTrue(p2.updatePersonalDetails("5xy%z#78AA", "Jane", "Smith", "88|New Rd|Melbourne|Victoria|Australia", "01-01-2007"));
    }
    @Test
    //above 18 user can also change the address
    public void testAdultCanChangeAddress2(){
       updateDetails p3 = new updateDetails("1sr$y@51AP","Sam", "Karan", "102|Plam St|Melbourne|Victoria|Australia","01-01-2000");
       assertTrue(p3.updatePersonalDetails("1sr$y@51AP ", "Sam", "Karan", "99|Germain St|Lynbrook|Victoria|Australia", "01-01-2000"));
}

    @Test
    //user is changing only his birthday no other changes
    public void testOnlyBirthdateIsChangedSuccessfully() {
        updateDetails p4 = new updateDetails("9qw&t!99ER", "Rishit", "Patel", "74|Castle Ave|Melbourne|Victoria|Australia", "02-02-2001");
        assertTrue(p4.updatePersonalDetails("9qw&t!99ER", "Rishit", "Patel", "74|Castle Ave|Melbourne|Victoria|Australia", "15-09-2003"));
    }

    @Test
    //same user is here changing only her birthday no more changes
    public void testOnlyBirthdateIsChangedSuccessfully1() {
        updateDetails p5 = new updateDetails("3mj$B!51QC","Isla","Patel","85|Queen St|Richmond|Victoria|Australia","23-06-1993");
        assertTrue(p5.updatePersonalDetails("3mj$B!51QC","Isla","Patel","85|Queen St|Richmond|Victoria|Australia","30-12-2004"));
    }


    @Test
    //here user wants to change the birthdate and also the name 
    public void testBirthdateAndNameChangeShouldFail() {
        updateDetails p6 = new updateDetails("7ab@!Xz81P", "Lucas", "Green", "14|Oak Ave|Geelong|Victoria|Australia", "01-01-1995");
        assertFalse(p6.updatePersonalDetails("7ab@!Xz81P", "Luke", "Green", "14|Oak Ave|Geelong|Victoria|Australia", "01-01-1996"));
    }
    @Test
    //here user wants to change address with birthdate
    public void testBirthdateAndAddressChangeShouldFail() {
        updateDetails p7 = new updateDetails("9pk#A@78XZ","Nathan","Lopez","12|Forest Rd|Dandenong|Victoria|Australia","12-12-2002");
        assertFalse(p7.updatePersonalDetails("9pk#A@78XZ","Nathan","Lopez","25|Mountain Dr|Noble Park|Victoria|Australia","12-12-2003"));
    }
    @Test
    //user wants to change the ID who's ID previously was even
    public void testEvenStartingIDCannotBeChanged() {
        updateDetails p8 = new updateDetails("2ab#xZ@98L", "Mia", "Lee", "77|Hill St|Melbourne|Victoria|Australia", "01-01-1990");
        assertFalse(p8.updatePersonalDetails("9ab#xZ@98L", "Mia", "Lee", "77|Hill St|Melbourne|Victoria|Australia", "01-01-1990"));
    }
    @Test
    //user wants to change the ID who's ID previously was odd
    public void testEvenStartingIDCannotBeChanged2() {
        updateDetails p9 = new updateDetails("3mn*W@96LB","Liam","Chen","99|Station Rd|Werribee|Victoria|Australia","04-04-1990");
        assertTrue(p9.updatePersonalDetails("2mn*W@96LB","Liam","Chen","99|Station Rd|Werribee|Victoria|Australia","04-04-1990"));
    }    


}