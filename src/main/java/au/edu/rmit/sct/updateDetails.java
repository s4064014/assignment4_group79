package au.edu.rmit.sct;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

    public boolean updatePersonalDetails(String newID, String newFirstName, String newLastName, String newAddress, String newBirthdate, String filename) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            File file = new File(filename);
            if (!file.exists()) file.createNewFile();

            // Condition 1: Under 18 cannot change address
            LocalDate dob = LocalDate.parse(this.birthdate, formatter);
            long age = ChronoUnit.YEARS.between(dob, LocalDate.now());
            if (age < 18 && !this.address.equals(newAddress)) return false;

            // Condition 2: If birthdate is changed, no other detail must be changed
            if (!this.birthdate.equals(newBirthdate)) {
                if (!this.personID.equals(newID) || !this.firstName.equals(newFirstName)
                        || !this.lastName.equals(newLastName) || !this.address.equals(newAddress)) {
                    return false;
                }
            }

            // Condition 3: If personID starts with even digit, it cannot be changed
            char firstChar = this.personID.charAt(0);
            if (Character.isDigit(firstChar)) {
                int digit = Character.getNumericValue(firstChar);
                if (digit % 2 == 0 && !this.personID.equals(newID)) return false;
            }

            // Read and update file
            List<String> lines = Files.readAllLines(Paths.get(filename));
            List<String> updatedLines = new ArrayList<>();
            boolean found = false;

            for (String line : lines) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 5 && parts[0].equals(this.personID)) {
                    found = true;
                    updatedLines.add(String.join(",", newID, newFirstName, newLastName, newAddress, newBirthdate));
                } else {
                    updatedLines.add(line);
                }
            }

            if (!found) return false;

            Files.write(Paths.get(filename), updatedLines);
            this.personID = newID;
            this.firstName = newFirstName;
            this.lastName = newLastName;
            this.address = newAddress;
            this.birthdate = newBirthdate;

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
