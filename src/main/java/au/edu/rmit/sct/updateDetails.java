package au.edu.rmit.sct;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class updateDetails {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;

    public updateDetails(String personID, String firstName, String lastName, String address, String birthdate) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
    }

    public boolean updatePersonalDetails(String newID, String newFirstName, String newLastName, String newAddress, String newBirthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            // Rule 1: Under 18 cannot change address
            LocalDate dob = LocalDate.parse(this.birthdate, formatter);
            long age = ChronoUnit.YEARS.between(dob, LocalDate.now());
            if (age < 18 && !this.address.equals(newAddress)) {
                return false;
            }

            // Rule 2: If birthdate is changed, no other detail must change
            if (!this.birthdate.equals(newBirthdate)) {
                boolean otherFieldsChanged = 
                    !this.personID.equals(newID) ||
                    !this.firstName.equals(newFirstName) ||
                    !this.lastName.equals(newLastName) ||
                    !this.address.equals(newAddress);
                if (otherFieldsChanged) {
                    return false;
                }
            }

            // Rule 3: If ID starts with even digit, it cannot be changed
            char firstChar = this.personID.charAt(0);
            if (Character.isDigit(firstChar)) {
                int digit = Character.getNumericValue(firstChar);
                if (digit % 2 == 0 && !this.personID.equals(newID)) {
                    return false;
                }
            }

            // All validations passed – update internal object
            this.personID = newID;
            this.firstName = newFirstName;
            this.lastName = newLastName;
            this.address = newAddress;
            this.birthdate = newBirthdate;

            // Prepare the line to write
            String newLine = personID + "," + firstName + "," + lastName + "," + address + "," + birthdate;

            // Check for duplicates before writing
            File file = new File("update.txt");
            if (!file.exists()) file.createNewFile();

            List<String> existingLines = Files.readAllLines(file.toPath());
            if (!existingLines.contains(newLine)) {
                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write(newLine + "\n");
                }
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
