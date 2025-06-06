package au.edu.rmit.sct;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;

    // Constructor to initialize Person fields
    public Person(String personID, String firstName, String lastName, String address, String birthdate) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
    }

    // Method to validate and add a Person record to persons.txt
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

        // Condition 3: Validate birthdate format: dd-MM-yyyy
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            Date dob = sdf.parse(birthdate);
        } catch (ParseException e) {
            return false;
        }

        // Prepare the line to write
        String newLine = personID + "," + firstName + "," + lastName + "," + address + "," + birthdate;

        // Write to persons.txt with duplicate removal logic
        File file = new File("persons.txt");
        try {
            if (!file.exists()) file.createNewFile();

            // Read all existing lines
            List<String> existingLines = Files.readAllLines(file.toPath());

            // Remove any existing line with the same personID
            List<String> updatedLines = existingLines.stream()
                    .filter(line -> !line.startsWith(personID + ","))
                    .collect(Collectors.toList());

            // Overwrite file without the old record (if any)
            Files.write(file.toPath(), updatedLines, StandardOpenOption.TRUNCATE_EXISTING);

            // Append new record
            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(newLine + "\n");
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
