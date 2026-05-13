package org.example.wardenconsole;
import java.io.BufferedReader; // read CVS line by line
import java.io.InputStream; // Load CSV
import java.io.InputStreamReader; // Conversion
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WardenReadCSV {
    // Store name of CSV
    private static final String fileName = "wardens.csv";

    // read all wardens from CSV file
    public List<Warden> readWardens() {
        // create array to hold records of wardens
        List<Warden> wardens = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        // check for CSV
        if (inputStream == null) {
            System.out.println("Couldn't find wardens.csv.");
            return wardens; // return empty
        }
        // open CSV
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            // read header line then skip
            String line = reader.readLine();

            // loop through CSV line by line
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue; // Skips blank lines.
                }
                // formatting
                String[] parts = line.split(",", -1);

                // check to ensure enough columns.
                if (parts.length < 11) {
                    System.out.println("Skipping invalid CSV row: " + line);
                    continue; // Skips
                }

                // Create warden from CSV line.
                Warden warden = new Warden(
                        Integer.parseInt(parts[0].trim()),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        parts[4].trim(),
                        parts[5].trim(),
                        parts[6].trim(),
                        parts[7].trim(),
                        parts[8].trim(),
                        parts[9].trim(),
                        parts[10].trim()
                );

                // add the object to the array list.
                wardens.add(warden);
            }
        }
        catch (Exception exception) {
            System.out.println("Error reading wardens.csv: " + exception.getMessage());
        }
        return wardens; // return list with wardens.
    }
}
