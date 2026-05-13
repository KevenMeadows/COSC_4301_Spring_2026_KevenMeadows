package org.example.wardenconsole;
import java.util.List;

public class WardenDisplayTable {

    // display wardens
    public void displayWardens(List<Warden> wardens) {
        // check if no wardens
        if (wardens.isEmpty()) {
            System.out.println("No wardens found.");
            return; // stop display
        }
        borderLine(200); // Start table border.
        System.out.printf(
                "%-5s %-20s %-20s %-12s %-15s %-30s %-10s %-15s %-15s %-12s %-12s%n", // formatting for display
                "ID", // Header for warden ID column.
                "First", // Header for first name column.
                "Last", // Header for last name column.
                "ID Type", // Header for identifier type column.
                "ID Value", // Header for identifier value column.
                "Email", // Header for email column.
                "Role", // Header for role column.
                "Status", // Header for employment status column.
                "Clearance", // Header for clearance level column.
                "Start Date", // Header for start date column.
                "End Date" // Header for end date column.
        );
        borderLine(200); // Close header border.

        // loop through wardens for display
        for (Warden warden : wardens) {
            System.out.printf(
                    "%-5s %-20s %-20s %-12s %-15s %-30s %-10s %-15s %-15s %-12s %-12s%n", // formatting for display
                    warden.getWardenId(), // add to warden ID column.
                    warden.getFirstName(), // add to first name column.
                    warden.getLastName(), // add to last name column.
                    warden.getIdentifierType(), // add to identifier type column.
                    warden.getIdentifierValue(), // add to identifier value column.
                    warden.getEmail(), // add to email column.
                    warden.getRole(), // add to role column.
                    warden.getEmploymentStatus(), // add to employment status column.
                    warden.getClearanceLevel(), // add to clearance level column.
                    warden.getStartDate(), // add to start date column.
                    warden.getEndDate() // add to end date column.
            );
        }
        borderLine(200); // Close table border.
    }

    // Make helper function for table borer.
    private void borderLine(int length) {
        System.out.println("-".repeat(length));
    }
}
