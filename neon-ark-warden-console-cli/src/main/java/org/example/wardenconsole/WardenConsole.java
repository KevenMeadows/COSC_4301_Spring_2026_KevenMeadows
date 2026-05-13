package org.example.wardenconsole;
import java.util.*;
import java.util.Scanner;

public class WardenConsole {

    // Create variables for displaying and reading outputs.
    private final Scanner scanner = new Scanner(System.in); // user input
    private final WardenReadCSV readCSV = new WardenReadCSV(); // csv reader
    private final WardenDisplayTable tableDisplay = new WardenDisplayTable(); // make table and display
    private final WardenValidate validate = new WardenValidate(); // validate for values
    private final SimRequestDisplay requestDisplay = new SimRequestDisplay(); // simulated display for request
    private final List<Warden> wardens = readCSV.readWardens(); // read wardens from CSV

    // main method
    public static void main(String[] args) {
        new WardenConsole().run(); // create and run console.
    }

    // run function - loop to keep menu going
    public void run() {
        // while loop - while true continue running menu.
        while (true) {
            // Display menu
            displayMenu();

            // prompt user to choose an option
            String option = prompt("Select an option: ");

            // create switch and case for each option
            switch (option) {
                case "1" -> addNewWarden(); // adds warden to records
                case "2" -> viewWardens(); // open viewing wardens
                case "3" -> updateWarden(); // update a warden
                case "4" -> manageCertifications(); // manage certifications menu
                case "5" -> terminateWarden(); // deactivate or terminate warden
                case "6" -> {System.out.println("Exiting menu...");
                             return;}
                default -> System.out.println("Input invalid. Choose an option from 1-6.");
            }
            pause(); // pause before menu return.
        }
    }

    // main menu for warden console
    private void displayMenu() {
        System.out.println("=========================================================");
        System.out.println("        NEON ARK — ADMIN WARDEN ONBOARDING CONSOLE       ");
        System.out.println("=========================================================");
        System.out.println();
        System.out.println("[ MAIN MENU ]");
        System.out.println("---------------------------------------------------------");
        System.out.println("1. Add New Warden");
        System.out.println("2. View Wardens");
        System.out.println("3. Update Warden");
        System.out.println("4. Manage Certifications");
        System.out.println("5. Deactivate / Terminate Warden");
        System.out.println("6. Exit");
    }

    // Create method for option 1 - Add new warden.
    private void addNewWarden() {
        System.out.println();
        System.out.println("| 1 | ADD NEW WARDEN |"); // header for prompts below.
        System.out.println("---------------------------------------------------------"); // break

        // start establishing warden values through user prompts
        int wardenId = promptWardenId(); // prompt for warden ID, send to helper function
        String firstName = required("Enter first name: "); // prompt for first name, send to helper function
        String lastName = prompt("Enter last name (Optional): "); // prompt for last name, send to helper function
        String identifierType = promptIdentifierType("Enter identifier type (Options - Badge, Passport, Visa): "); // prompt for identifier type, send to helper function
        String identifierValue = promptUniqueIdentifier(identifierType); // prompt for identifier value, send to helper function
        String email = promptUniqueEmail(); // prompt for email, send to helper function
        String role = promptAllowed("Enter role (Options - Admin, Field, Rift, Trainer, Astral): ", "role"); // prompt for role, send to helper function
        String status = promptAllowed("Enter status of employment (Options - Active, OnLeave, Terminated): ", "status"); // prompt for status, send to helper function
        String clearance = promptAllowed("Enter clearance level (Options - Alpha, Omega, Eclipse): ", "clearance"); // prompt for clearance, send to helper function
        String startDate = requiredDate("Enter start date (YYYY-MM-DD): "); // prompt for startDate, send to helper function
        String endDate = optionalDate("Enter end date (Optional - YYYY-MM-DD): "); // prompt for endDate, send to helper function

        // send confirmation prompt to confirm.
        if (!confirm("Are you sure you want to add new warden? ")) {
            requestDisplay.displayBlocked("Warden creation canceled. User did not confirm.");
            return;
        }

        // Map all values to the payload.
        Map<String, Object> payload = new LinkedHashMap<>(); // fake JSON for payload.
        payload.put("wardenId", wardenId); //
        payload.put("firstName", firstName); //
        payload.put("lastName", lastName); //
        payload.put("identifierType", identifierType); //
        payload.put("identifierValue", identifierValue); //
        payload.put("email", email); //
        payload.put("role", role); //
        payload.put("employmentStatus", status); //
        payload.put("clearanceLevel", clearance); //
        payload.put("startDate", startDate); //
        payload.put("endDate", endDate); //

        // display simulated request
        requestDisplay.displayRequest(
                "POST", // Simulated HTTP method
                "/api/wardens", // Simulated endpoint
                "Created new Warden record from user front-end input.",
                payload // Simulated JSON payload
        );
    }

    // Create method for option 2 - View Wardens
    private void viewWardens() {
        // keep submenu running until false for return.
        while (true) {
            System.out.println();
            System.out.println("| 2 | VIEW WARDENS |");
            System.out.println("---------------------------------------------------------");
            System.out.println("1. View All Wardens");
            System.out.println("2. View Warden by ID");
            System.out.println("3. View Wardens by Employment Status");
            System.out.println("4. View Wardens by Role");
            System.out.println("5. Return to MAIN MENU");

            // prompt user to choose an option
            String option = prompt("Select an option: ");

            // create switch and case for each option for sub menu.
            switch (option) {
                case "1" -> tableDisplay.displayWardens(wardens); // Display CSV Wardens.
                case "2" -> simViewWardenById(); // Simulate view by ID.
                case "3" -> simViewByStatus(); // Simulate filter with status.
                case "4" -> simViewByRole(); // Simulate filter with role.
                case "5" -> {return;} // back to main menu.
                default -> System.out.println("Input invalid. Choose an option from 1-5.");
            }
            pause(); // pause before menu return.
        }
    }

    // Create all methods for submenu from Option 1 - simViewWardenById(), simViewByStatus() and simViewByRole().
    private void simViewWardenById() {
        // prompt warden ID.
        int id = promptIntValue("Enter warden ID: ");

        // Create empty payload map.
        Map<String, Object> payload = new LinkedHashMap<>();

        // add warden ID for display
        payload.put("wardenId", id);

        // Attempting actual display through CSV.
        List<Warden> match = wardens.stream().filter(warden -> warden.getWardenId() == id).toList();
        if (match.isEmpty()) {
            System.out.println();
            System.out.println("RESULT: No Warden found under ID " + id + ".");
            // display simulated request
            requestDisplay.displayRequest("GET", "/api/wardens/" + id, "Get complete Warden records.", payload);
            return;
        }

        // display actual CSV data.
        tableDisplay.displayWardens(match);
    }
    private void simViewByStatus() {
        // prompt status of employment.
        String status = promptAllowed("Enter employment status: ", "status");

        // Create empty payload map.
        Map<String, Object> payload = new LinkedHashMap<>();

        // add status to payload.
        payload.put("employmentStatus", status);

        // Attempting actual display through CSV.
        List<Warden> matches = wardens.stream().filter(warden -> warden.getEmploymentStatus().equalsIgnoreCase(status)).toList();
        if (matches.isEmpty()) {
            System.out.println();
            System.out.println("RESULT: No Warden found under employment status " + status + ".");
            /// display simulated request
            requestDisplay.displayRequest("GET", "/api/wardens?employmentStatus=" + status,
                    "Get Warden records that match employment status.", payload);
            return;
        }

        // display actual CSV data.
        tableDisplay.displayWardens(matches);
    }
    private void simViewByRole() {
        // prompt role set.
        String role = promptAllowed("Enter role: ", "role");

        // Create empty payload map.
        Map<String, Object> payload = new LinkedHashMap<>();

        // add role to payload
        payload.put("role", role); // Adds role.

        // Attempting actual display through CSV.
        List<Warden> matches = wardens.stream().filter(warden -> warden.getRole().equalsIgnoreCase(role)).toList();
        if (matches.isEmpty()) {
            System.out.println();
            System.out.println("RESULT: No Warden found with role " + role + ".");
            /// display simulated request
            // display simulated request.
            requestDisplay.displayRequest("GET", "/api/wardens?role=" + role, "Get Wardens that match a role.", payload);
            return;
        }

        // display actual CSV data.
        tableDisplay.displayWardens(matches);
    }

    // Create method for option 3 - Update Warden
    private void updateWarden() {
        // Requires Warden ID to start, prompt for it.
        int wardenId = promptIntValue("Enter Warden ID to update: ");

        // keep submenu running until false for return.
        while (true) {
            System.out.println();
            System.out.println("| 3 | UPDATE WARDEN |");
            System.out.println("---------------------------------------------------------");
            System.out.println("1. Update Role");
            System.out.println("2. Update Clearance Level");
            System.out.println("3. Update Employment Status");
            System.out.println("4. Update Start Date");
            System.out.println("5. Update End Date");
            System.out.println("6. Update Email");
            System.out.println("7. Cancel Update");

            // prompt user to choose an option
            String option = prompt("Select an option: ");

            // create switch and case for each option for sub menu.
            switch (option) {
                case "1" -> simUpdateField(wardenId, "role", promptAllowed("Enter new role: ", "role"), "/role");
                case "2" -> simUpdateField(wardenId, "clearanceLevel", promptAllowed("Enter new clearance level: ", "clearance"), "/clearance-level");
                case "3" -> simUpdateField(wardenId, "employmentStatus", promptAllowed("Enter new employment status: ", "status"), "/employment-status");
                case "4" -> simUpdateField(wardenId, "startDate", requiredDate("Enter new start date (YYYY-MM-DD): "), "/start-date");
                case "5" -> simUpdateField(wardenId, "endDate", optionalDate("Enter end date (YYYY-MM-DD): "), "/end-date");
                case "6" -> simUpdateField(wardenId, "email", promptEmail("Enter new email: "), "/email");
                case "7" -> {return;} // back to main menu.
                default -> {
                    System.out.println("Input invalid. Choose an option from 1-7.");
                    continue;
                }
            }

            // return to main menu after update.
            return;
        }
    }

    // Create a method for update submenu to make changes to Warden records.
    private void simUpdateField(int wardenId, String updateField, String updateValue, String endpointSuffix) {
        // Create empty payload map.
        Map<String, Object> payload = new LinkedHashMap<>();

        // add field update to payload
        payload.put(updateField, updateValue);

        // display simulated request.
        requestDisplay.displayRequest("PUT", "/api/wardens/" + wardenId + endpointSuffix,
                "Update Warden " + updateField + " after validating.", payload);

    }

    // Create method for Option 4 - Manage Certifications
    private void manageCertifications() {
        // Requires Warden ID to start, prompt for it.
        int wardenId = promptIntValue("Enter Warden ID to manage certifications for: ");

        // keep submenu running until false for return.
        while (true) {
            System.out.println();
            System.out.println("| 4 | MANAGE CERTIFICATIONS |");
            System.out.println("---------------------------------------------------------");
            System.out.println("1. Add Certification");
            System.out.println("2. View Certifications");
            System.out.println("3. Mark Certification Expired");
            System.out.println("4. Remove Certification");
            System.out.println("5. Return to MAIN MENU");

            // prompt user to choose an option
            String option = prompt("Select an option: ");

            // create switch and case for each option for sub menu.
            switch (option) {
                case "1" -> simAddCertification(wardenId); // add certs
                case "2" -> simViewCertifications(wardenId); // view certs
                case "3" -> simExpireCertification(wardenId); // expire certs
                case "4" -> simRemoveCertification(wardenId); // remove certs
                case "5" -> {return;} // back to main menu.
                default -> System.out.println("Input invalid. Choose an option from 1-5.");
            }
            // for returning to sub menu.
            pause();
        }
    }

    // Create helper functions for sub menu of certifications.
    private void simAddCertification(int wardenId) {
        // Prompt for certification name.
        String cert = required("Enter certification name: ");
        // Date cert was earned.
        String certDate = requiredDate("Enter date certification was earned (YYYY-MM-DD): ");
        // Get expiration date for cert.
        String certExpire = optionalDate("Enter expiration date of certification (Optional, YYYY-MM-DD): ");

        // Create payload and map values to it.
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("name", cert);
        payload.put("earnedDate", certDate);
        payload.put("expirationDate", certExpire);

        requestDisplay.displayRequest("POST", "/api/wardens/" + wardenId + "/certifications",
                "Create a certification record for a Warden.", payload);
    }
    private void simViewCertifications(int wardenId) {
        // Create payload and map values to it.
        Map<String, Object> payload = new LinkedHashMap<>();

        // add warden ID to payload.
        payload.put("wardenId", wardenId);
        requestDisplay.displayRequest("GET", "/api/wardens/" + wardenId + "/certifications",
                "Get certifications for this Warden.", payload);
    }
    private void simExpireCertification(int wardenId) {
        // find certificate with ID
        int certificationId = promptIntValue("Enter certification record ID: ");

        // Create payload and map values to it.
        Map<String, Object> payload = new LinkedHashMap<>();

        // add certification ID to payload.
        payload.put("certificationId", certificationId);

        // add expired status to payload
        payload.put("status", "Expired");

        // display
        requestDisplay.displayRequest("PUT", "/api/wardens/" + wardenId + "/certifications/" + certificationId
                + "/expire", "Mark certification as expired.", payload);
    }
    private void simRemoveCertification(int wardenId) {
        // find certificate with ID
        int certificationId = promptIntValue("Enter certification record ID: ");

        // prompt confirmation helper function
        if (!confirm("Are you sure you want to remove certification? ")) {
            requestDisplay.displayBlocked("Canceled removal.");
            return;
        }

        // Create payload for removal.
        Map<String, Object> payload = new LinkedHashMap<>();

        // Put certificationId to certificationId.
        payload.put("certificationId", certificationId);

        // display
        requestDisplay.displayRequest("DELETE", "/api/wardens/" + wardenId + "/certifications/" + certificationId,
                "Remove a certification.", payload);
    }

    // Create method for Option 5 - Deactivate / Terminate Warden
    private void terminateWarden() {
        // Requires Warden ID to start, prompt for it.
        int wardenId = promptIntValue("Enter Warden ID: ");

        // menu
        System.out.println();
        System.out.println("| 5 | DEACTIVATE / TERMINATE WARDEN |");
        System.out.println("---------------------------------------------------------");
        System.out.println("1. Deactivate - Change Status of Employment");
        System.out.println("2. Terminate - Change Status of Employment / Set End Date");
        System.out.println("3. Cancel");

        // prompt user to choose an option
        String option = prompt("Select an option: ");

        // create switch and case for each option for sub menu.
        switch (option) {
            case "1" -> simDeactivate(wardenId); // Deactivate
            case "2" -> simTerminate(wardenId); // Terminate
            case "3" -> System.out.println("Canceled. Returning to Main Menu."); // cancel
            default -> System.out.println("Input invalid. Returning to Main Menu."); // if invalid
        }
    }

    // Create methods for sub menu option simDeactivate and simTerminate.
    private void simDeactivate(int wardenId) {
        // prompt for new status of employment.
        String status = promptAllowed("Enter new employment status: ", "status");

        // confirm deactivation with prompt to user.
        if (!confirm("Are you sure you want to make this change? ")) {
            requestDisplay.displayBlocked("Canceled deactivation.");
            return;
        }

        // update with deactivation
        simUpdateField(wardenId, "employmentStatus", status, "/employment-status");
    }
    private void simTerminate(int wardenId){
        // Prompt for status.
        String status = promptAllowed("Enter employment status: ", "status");
        // Prompt for end date.
        String endDate = requiredDate("Enter termination date (YYYY-MM-DD): ");

        // confirm termination with prompt to user.
        if (!confirm("Are you sure you want to continue with termination? ")) {
            requestDisplay.displayBlocked("Canceled termination.");
            return;
        }

        // Create a payload for mapping termination.
        Map<String, Object> payload = new LinkedHashMap<>();

        // load payload with status and end date.
        payload.put("employmentStatus", status);
        payload.put("endDate", endDate);

        // display
        requestDisplay.displayRequest("PUT", "/api/wardens/" + wardenId + "/termination",
                "Termination of Warden through status change and end date setting.", payload);
    }

    // Create prompt helper functions - Will need ID, Int inputs, String inputs, valid and required inputs etc.
    // Create helper function for warden ID prompt.
    private int promptWardenId() {
        // repeat ask until valid input.
        while (true) {
            // prompt has to be positive int.
            int id = promptIntValue("Enter warden ID: ");

            // check for duplicate and valid
            if (validate.wardenIdExists(wardens, id)) {
                System.out.println("Warden ID entered already exists. Enter a unique ID.");
                continue; // restart prompt until valid input.
            }
            // return valid one of a kind ID.
            return id;
        }
    }

    // Create method for prompting identifiers.
    private String promptUniqueEmail() {
        // repeat ask until valid input given.
        while (true) {
            String email = promptEmail("Enter email: ");
            // check if valid
            if (validate.emailExists(wardens, email)) {
                System.out.println("Email exists. Enter a unique email.");
                continue; // restart prompt until valid input.
            }
            // return valid unique identifier.
            return email;
        }
    }

    // Create method for prompting unique identifier values.
    private String promptUniqueIdentifier(String identifierType) {
        // repeat ask until valid input given.
        while (true) {
            String input = required("Enter identifier value: ");
            // check if valid
            if (validate.identifierExists(wardens, identifierType, input)) {
                System.out.println("Not a unique identifier. Enter unique.");
                continue; // restart prompt until valid input.
            }
            // return valid unique identifier.
            return input;
        }
    }

    // Create method for prompting identifier types.
    private String promptIdentifierType(String message) {
        // repeat ask until valid input given.
        while (true) {
            // format and send to prompt for reading.
            String input = required(message).toUpperCase();

            // check if valid
            if (!validate.checkIdentifier(input)) {
                System.out.println("Invalid identifier type. Must choose Badge, Passport or Visa.");
                continue; // restart prompt
            }
            // return valid type.
            return input;
        }
    }

    // Create method for prompt checking controlled values (role, status and clearance)
    private String promptAllowed(String message, String label) {
        // repeat ask until valid input given.
        while (true) {
            // read input
            String input = required(message);

            // check role, status and clearance
            if (label.equals("role") && validate.checkRole(input)) {
                // return valid role
                return input;
            }
            if (label.equals("status") && validate.checkStatus(input)) {
                // return valid employment status
                return input;
            }
            if (label.equals("clearance") && validate.checkClearance(input)) {
                // return valid clearance level
                return input;
            }
            System.out.println("Invalid input: " + label + ". Enter in input that matches warden design.");
        }
    }

    // Create method for prompting email input.
    private String promptEmail(String message) {
        // repeat ask until valid input given.
        while (true) {
            // read date sent in.
            String email = required(message);

            // check if valid email
            if (!validate.checkEmail(email)) {
                // Validation error, possibly formatting.
                System.out.println("Input invalid. Email is required. Ensure use of '@' and email domain.");
                continue; // restart prompt until valid input.
            }

            // return valid email
            return email;
        }
    }

    // Create prompt helper function for required dates.
    private String requiredDate(String message) {
        // repeat ask until valid input, valid date.
        while (true) {
            // read date sent in.
            String reqDate = required(message);

            // check if valid date
            if (!validate.checkDate(reqDate)) {
                // Validation error, possibly formatting.
                System.out.println("Input invalid. Ensure use of format: YYYY-MM-DD.");
                continue; // restart prompt until valid input.
            }

            // return date with valid format or no date (blank)
            return reqDate;
        }
    }

    // Create prompt helper function for optional dates.
    private String optionalDate(String message) {
        // repeat ask until valid input or left blank
        while (true) {
            // read date sent in.
            String optDate = prompt(message);

            // check if valid date
            if (!validate.isOptionalDate(optDate)) {
                // Validation error, possibly formatting.
                System.out.println("Input invalid. Ensure use of format: YYYY-MM-DD.");
                continue; // restart prompt
            }

            // return date with valid format or no date (blank)
            return optDate;
        }
    }

    // Create prompt helper function for integer input on prompts.
    private int promptIntValue(String message) {
        // repeat ask until valid input (positive int)
        while (true) {
            // send input to check with required helper function.
            String input = required(message);

            // try exception
            try {
                // convert the text to an integer
                int inputToInt = Integer.parseInt(input);

                // check value to ensure positive integer.
                if (inputToInt <= 0) {
                    // Validation error
                    System.out.println("Input must be greater than 0.).");
                    continue; // restart prompt until valid input.
                }
                // valid integer pass.
                return inputToInt;
            }
            catch (NumberFormatException exception) {
                // Validation error for non-int or non-numeric
                System.out.println("Invalid input. Enter a positive integer value for input.");
            }
        }
    }

    // Create prompt helper function for required information.
    private String required(String message) {
        // repeat ask until valid input.
        while (true) {
            // read input from user.
            String input = prompt(message);

            // check with validate class
            if (validate.isBlank(input)) {
                System.out.println("Input is required.");
                continue; // prompt again
            }

            // valid input (not null)
            return input;
        }
    }

    // Create prompt  helper function for simpler String prompting.
    private String prompt(String message) {
        System.out.print(message);

        // sanity check
        if (!scanner.hasNextLine()) {
            System.out.println();
            System.out.println("No console input available.");
            System.exit(1);
        }
        return scanner.nextLine().trim();
    }

    // Create helper confirmation methods
    private boolean confirm(String message) {
        String response = prompt(message + " Enter 'Y' to Confirm: ");
        return response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("YES");
    }

    // Create helper function for continue running
    private void pause(){
        System.out.println();
        System.out.println("| Press [Enter] to Continue |");
        scanner.nextLine();
    }
}
