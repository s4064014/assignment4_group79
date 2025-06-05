package au.edu.rmit.sct;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;

    public Person(String personID, String firstName, String lastName, String address, String birthdate) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
    }

    public boolean addPerson() {
        // Condition 1: Validate personID
        if (personID == null || personID.length() != 10) return false;

        // First 2 chars must be digits between 2 and 9
        if (!Character.isDigit(personID.charAt(0)) || !Character.isDigit(personID.charAt(1))) return false;
        int firstDigit = Character.getNumericValue(personID.charAt(0));
        int secondDigit = Character.getNumericValue(personID.charAt(1));
        if (firstDigit < 2 || secondDigit < 2) return false;

        // Characters 3–8 must contain at least 2 special characters
        int specialCount = 0;
        for (int i = 2; i <= 7; i++) {
            char ch = personID.charAt(i);
            if (!Character.isLetterOrDigit(ch)) specialCount++;
        }
        if (specialCount < 2) return false;

        // Last 2 characters must be uppercase A-Z
        if (!Character.isUpperCase(personID.charAt(8)) || !Character.isUpperCase(personID.charAt(9))) return false;

        // Condition 2: Validate address format: StreetNo|Street|City|State|Country
        String[] parts = address.split("\\|");
        if (parts.length != 5) return false;
        String state = parts[3];
        if (!state.equals("Victoria")) return false;

        // Condition 3: Validate birthdate format
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            Date  dob= sdf.parse(birthdate);
        } catch (ParseException e) {
            return false;
        }

        // All conditions passed, write to file
        try (FileWriter writer = new FileWriter("persons.txt", true)) {
            writer.write(personID + "," + firstName + "," + lastName + "," + address + "," + birthdate + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}