package au.edu.rmit.sct;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class demeritPoints {
    private String personID;
    private String offenseDate;
    private int demeritPoints;
    public boolean isSuspended = false;
    private int age;
    private List<String> demeritRecords = new ArrayList<>();

    // Constructor
    public demeritPoints(String personID, String offenseDate, int demeritPoints, String birthdate) {
        this.personID = personID;
        this.offenseDate = offenseDate;
        this.demeritPoints = demeritPoints;
        this.age = calculateAgeFromBirthdate(birthdate);
    }

    private int calculateAgeFromBirthdate(String birthdate) {
        int birthYear = Integer.parseInt(birthdate.substring(6));
        int currentYear = java.time.LocalDate.now().getYear();
        return currentYear - birthYear;
    }

    public String adddemeritPoints(String offenseDate, int points, int currentYear) {
        if (offenseDate.length() != 10 || offenseDate.charAt(2) != '-' || offenseDate.charAt(5) != '-') {
            return "Failed";
        }
        if (points < 1 || points > 6) {
            return "Failed";
        }

        try {
            File file = new File("demerit.txt");
            List<String> updatedLines = new ArrayList<>();
            boolean found = false;

            // If file exists, read existing lines
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(personID)) {
                        // Load existing demeritRecords from file first
                        demeritRecords.clear();
                        for (int i = 1; i < parts.length; i += 2) {
                            String existingDate = parts[i];
                            String existingPoints = parts[i + 1];
                            demeritRecords.add(existingDate + ":" + existingPoints);
                        }
                        found = true;
                    } else {
                        updatedLines.add(line);
                    }
                }
                reader.close();
            }

            // Now add the new record to demeritRecords only if not already present
            String newRecord = offenseDate + ":" + points;
            if (!demeritRecords.contains(newRecord)) {
                demeritRecords.add(newRecord);
            }

            // Calculate suspension
            int totalPoints = 0;
            for (String record : demeritRecords) {
                String[] parts = record.split(":");
                String date = parts[0];
                int recordYear = Integer.parseInt(date.substring(6));
                int recordPoints = Integer.parseInt(parts[1]);

                if (currentYear - recordYear <= 2) {
                    totalPoints += recordPoints;
                }
            }

            if ((age < 21 && totalPoints > 6) || (age >= 21 && totalPoints > 12)) {
                isSuspended = true;
            }

            // Now rebuild this person's line
            StringBuilder sb = new StringBuilder();
            sb.append(personID);
            for (String rec : demeritRecords) {
                String[] recParts = rec.split(":");
                sb.append(",").append(recParts[0]).append(",").append(recParts[1]);
            }

            // Add or update this person's line in file
            updatedLines.add(sb.toString());

            // Write all lines back to file (overwrite)
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String line : updatedLines) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();

            return "Success";

        } catch (IOException e) {
            System.out.println("File write error.");
            return "Failed";
        }
    }

}
