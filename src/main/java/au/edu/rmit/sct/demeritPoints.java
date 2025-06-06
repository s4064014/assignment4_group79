package au.edu.rmit.sct;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class demeritPoints {
    private String personID;
    private String offenseDate;
    private int demeritPoints;
    //private String birthdate;

    // Constructor
    public demeritPoints(String personID, String offenseDate, int demeritPoints, String birthdate) {
        this.personID = personID;
        this.offenseDate = offenseDate;
        this.demeritPoints = demeritPoints;
        //this.birthdate = birthdate;
    }

    // Function with "add" prefix parameters for clarity
    public String addDemeritPoints(String addPersonID, String addOffenseDate, int addDemeritPoints, String addBirthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Validate offense date
        LocalDate parsedOffenseDate;
        try {
            parsedOffenseDate = LocalDate.parse(addOffenseDate, formatter);
        } catch (Exception e) {
            System.out.println("Invalid offense date format.");
            return "Failed";
        }

        // Validate demerit points range
        if (addDemeritPoints < 1 || addDemeritPoints > 6) {
            System.out.println("Invalid demerit points.");
            return "Failed";
        }
        
        // Validate birthdate format
        LocalDate parsedBirthdate;
        try {
            parsedBirthdate = LocalDate.parse(addBirthdate, formatter);
        } catch (Exception e) {
            System.out.println("Invalid birthdate format.");
            return "Failed";
        }

        // Update internal class variables
        this.personID = addPersonID;
        this.offenseDate = addOffenseDate;
        this.demeritPoints = addDemeritPoints;
        //this.birthdate = addBirthdate;

        long age = ChronoUnit.YEARS.between(parsedBirthdate, LocalDate.now());
        LocalDate twoYearsAgo = parsedOffenseDate.minusYears(2);

        File file = new File("persons.txt");
        try {
            if (!file.exists()) file.createNewFile();

            List<String> lines = Files.readAllLines(file.toPath());
            List<String> updatedLines = new ArrayList<>();
            boolean found = false;

            for (String line : lines) {
                String[] parts = line.split(",", -1);
                if (parts.length < 5) {
                    updatedLines.add(line);
                    continue;
                }

                if (parts[0].equals(this.personID)) {
                    found = true;
                    int existingPoints = 0;

                    if (parts.length > 5) {
                        for (int i = 5; i < parts.length; i += 2) {
                            LocalDate pastDate = LocalDate.parse(parts[i], formatter);
                            if (!pastDate.isBefore(twoYearsAgo)) {
                                existingPoints += Integer.parseInt(parts[i + 1]);
                            }
                        }
                    }

                    int totalPoints = existingPoints + this.demeritPoints;
                    boolean shouldSuspend = (age < 21 && totalPoints > 6) || (age >= 21 && totalPoints > 12);
                    if (shouldSuspend) {
                        System.out.println("Person is now suspended.");
                    }

                    // Append new demerit info
                    String updatedLine = line + "," + this.offenseDate + "," + this.demeritPoints;
                    updatedLines.add(updatedLine);
                } else {
                    updatedLines.add(line);
                }
            }

            if (!found) return "Failed";

            Files.write(file.toPath(), updatedLines);
            return "Success";

        } catch (IOException e) {
            e.printStackTrace();
            return "Failed";
        }
    }
}