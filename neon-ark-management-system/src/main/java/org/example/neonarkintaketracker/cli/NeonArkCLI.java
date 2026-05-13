package org.example.neonarkintaketracker.cli;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class NeonArkCLI {
    // Store keyboard input, API client and table display
    private final Scanner scanner;
    private final APIClient apiClient;
    private final TableDisplay tableDisplay;

    // Constructor
    public NeonArkCLI() {
        this.scanner = new Scanner(System.in);
        this.apiClient = new APIClient();
        this.tableDisplay = new TableDisplay();
    }

    // main method for running CLI
    public static void main(String[] args) {
        new NeonArkCLI().run();
    }

    // Run CLI
    public void run() {
        // create a boolean for continue running or not
        // boolean running = true; // better to use confirm options in if statement, avoid boolean variable.

        // keep running until running is false
        while (true) {
            displayMenu(); // shows menu
            String option = prompt("Select an option: ");

            // create switch list for menu
            switch (option) {
                case "1" -> viewAllCreatures();
                case "2" -> viewOneCreature();
                case "3" -> addCreature();
                case "4" -> renameCreature();
                case "5" -> removeCreature();
                case "6" -> viewCreatureObservations();
                case "7" -> viewCreatureFeedingTimes();
                case "8" -> viewAllUsers();
                case "0" -> { if (confirmExit()) {
                    System.out.println("Neon Ark CLI terminated.");
                    return;
                    }
                }
                default -> System.out.println("Option invalid. Please choose a valid option number.");
            }
            // running still pause before next menu option
            pause();
        }
    }

    // Create CLI menu display method
    private void displayMenu() {
        System.out.println();
        System.out.println("=====================================");
        System.out.println("         NEON ARK CLI SYSTEM         ");
        System.out.println("=====================================");
        System.out.println("1. List all creatures");
        System.out.println("2. View creature by ID");
        System.out.println("3. Register new creature");
        System.out.println("4. Rename creature");
        System.out.println("5. Remove creature");
        System.out.println("6. View creature observations/notes");
        System.out.println("7. Find creatures by feeding time");
        System.out.println();
        System.out.println("--- Admin Only ---");
        System.out.println("8. View all system users");
        System.out.println();
        System.out.println("0. Exit");
        System.out.println("-------------------------------------");
    }
    
    // Create method for option 1 - LIST ALL CREATURES
    private void viewAllCreatures() {
        APIClient.APIResult result = apiClient.get("/api/creatures"); // send request

        if (result.success()) {
            tableDisplay.displayCreatures(result.JSON()); // displays list of creatures as a table
        }
        else {
            tableDisplay.displayError(result); // response error
        }
    }

    // Create method for option 2 - VIEW CREATURE BY ID
    private void viewOneCreature() {
        Long id = promptID("Enter the Creature ID: "); // get ID from user for request

        // check input
        if (id == null) {
            return; // cancel
        }

        APIClient.APIResult result = apiClient.get("/api/creatures/" + id); // send request

        // check if success
        if (result.success()) {
            tableDisplay.displayOneCreature(result.JSON()); // display creature
        }
        else {
            tableDisplay.displayError(result); // API error return
        }
    }

    // Create method for option 3 - REGISTER NEW CREATURE
    private void addCreature() {
        String name = promptInfo("Enter Creature Name: "); // User input creature name.
        String species = promptInfo("Enter Species of Creature: "); // User input creature species.
        String dangerLevel = promptInfo("Enter Danger Level of Creature (Options - LOW, MEDIUM, HIGH, EXTREME): "); // User input danger level.
        String status = promptInfo("Enter Status of Creature (Options - STABLE, QUARANTINED, CRITICAL, ACTIVE, AGGRESSIVE, DORMANT, REMOVED): "); // User input creature status.
        String notes = prompt("Enter Notes About Creature: "); // User input creature notes.
        Long habitatID = promptID("Enter Creature Habitat ID: "); // User input creature habitat id.

        // check all to see if null
        if (name == null || species == null || dangerLevel == null || status == null || habitatID == null) {
            System.out.println("Creature registration canceled: Input missing or invalid.");
            return; // cancels request
        }

        // Map JSON with values
        Map<String, Object> requestJSON = new LinkedHashMap<>();
        requestJSON.put("name", name);
        requestJSON.put("species", species);
        requestJSON.put("dangerLevel", dangerLevel.toUpperCase());
        requestJSON.put("status", status.toUpperCase());
        requestJSON.put("notes", notes);
        requestJSON.put("habitatId", habitatID);

        // POST request
        APIClient.APIResult result = apiClient.post("/api/creatures", requestJSON);

        // check status code
        if (result.success()) {
            System.out.println("Creature has been successfully registered!"); // for user to read.
            tableDisplay.displayOneCreature(result.JSON()); // show creature that was created.
        }
        else {
            tableDisplay.displayError(result); // return error
        }
    }

    // Create method for option 4 - RENAME CREATURE
    private void renameCreature() {
        Long id = promptID("Enter the ID of the creature to rename: ");
        // check id
        if (id == null) {
            return; // cancel
        }

        // read new creature name
        String newName = promptInfo("Enter the new name of the creature: ");

        // check newName
        if (newName == null) {
            return; // cancel
        }

        // ask to make sure of rename
        boolean confirmation = confirm("Are you sure you want to rename creature ID " + id + " to '" + newName + "'?");

        // check confirmation
        if (!confirmation) {
            System.out.println("Renaming has been canceled (No request was sent).");
            return; // cancel
        }

        // map to JSON body
        Map<String, Object> requestJSON = new LinkedHashMap<>();
        requestJSON.put("newName", newName);

        // send PUT request
        APIClient.APIResult result = apiClient.put("/api/creatures/" + id + "/name", requestJSON);

        // check to ensure success
        if (result.success()) {
            tableDisplay.displayRenameResponse(result.JSON()); // display old and new name
        }
        else {
            tableDisplay.displayError(result); // API error response.
        }
    }

    // Create method for option 5 - REMOVE CREATURE
    private void removeCreature() {
        Long id = promptID("Enter the ID of the creature to remove: ");
        // check id
        if (id == null) {
            return; // cancel
        }

        // get a confirmation
        boolean confirmation = confirm("Are you sure you want to remove creature ID " + id);

        // check confirmation
        if (!confirmation) {
            System.out.println("Removal has been canceled (No request was sent).");
            return; // cancel
        }

        // send DELETE request
        APIClient.APIResult result = apiClient.delete("/api/creatures/" + id);

        // check to ensure success
        if (result.success()) {
            tableDisplay.displayRemoveResponse(result.JSON()); // display old and new name
        }
        else {
            tableDisplay.displayError(result); // API error response.
        }
    }

    // Create a method for option 6 - VIEW CREATURE OBSERVATIONS/NOTES
    private void viewCreatureObservations() {
        Long id = promptID("Enter creature ID: ");
        // check id
        if (id == null) {
            return; // cancel
        }

        // send GET request
        APIClient.APIResult result = apiClient.get("/api/creatures/" + id + "/observations");

        // check to ensure success
        if (result.success()) {
            tableDisplay.displayObservations(result.JSON()); // display old and new name
        }
        else {
            tableDisplay.displayError(result); // API error response.
        }
    }

    // Create method for option 7 - FIND CREATURES BY FEEDING TIME
    private void viewCreatureFeedingTimes() {
        String time = promptInfo("Enter feeding time (HH:MM): ");
        // check time
        if (time == null) {
            return; // cancel
        }
        // ensure time formal is correct
        if (!time.matches("^\\d{2}:\\d{2}$")) {
            System.out.println("Invalid date entered. Use format HH:MM.");
            return; // cancel
        }

        // send GET request
        APIClient.APIResult result = apiClient.get("/api/feedings?time=" + time);

        // check to ensure success
        if (result.success()) {
            tableDisplay.displayFeedings(result.JSON()); // display old and new name
        }
        else {
            tableDisplay.displayError(result); // API error response.
        }
    }

    // Create method for option 8 - VIEW ALL SYSTEM USERS
    private void viewAllUsers() {
        String role = promptInfo("Enter admin role: ");
        // check time
        if (role == null) {
            return; // cancel
        }

        // send GET request
        APIClient.APIResult result = apiClient.getWithRole("/api/admin/users", role);

        // check to ensure success
        if (result.success()) {
            tableDisplay.displayUsers(result.JSON()); // display old and new name
        }
        else {
            tableDisplay.displayError(result); // API error response.
        }
    }

    // Create helper confirmation methods
    private boolean confirmExit() {
        return confirm("Are you sure you want to exit?"); // get confirmation response););
    }
    private boolean confirm(String message) {
        String response = prompt(message + " Enter 'Y' to Confirm: ");
        return response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("YES");
    }

    // Create helper function for prompting.
    private String prompt(String message) {
            System.out.print(message);

            // sanity check
            if (!scanner.hasNextLine()) {
                System.out.println();
                System.out.println("No console input available. Run NeonArkCLI as Application.");
                System.exit(1);
            }
            return scanner.nextLine().trim();
    }
    // Create helper function for prompting for user info input.
    private String promptInfo(String message) {
        String input = prompt(message);
        // check if blank
        if (input.isBlank()) {
            System.out.println("Input is required."); // address null value
            return null; // return null for invalid input signal.
        }
        return input;
    }
    // Create helper function for prompting for IDs.
    private Long promptID(String message) {
        String input = promptInfo(message); // read input

        // check input
        if (input == null) {
            return null; // invalid input signal
        }

        // try exception
        try {
            long value = Long.parseLong(input); // text to long

            // check value
            if (value <= 0) {
                System.out.println("ID cannot be less than or equal to 0.");
                return null;
            }

            return value;
        }
        catch (NumberFormatException exception){
            System.out.println("Input invalid. Enter numeric value.");
            return null;
        }
    }

    // Create helper function for continue running
    private void pause(){
        System.out.println();
        System.out.println("| Press [Enter] to Continue |");
        scanner.nextLine();
    }
}
