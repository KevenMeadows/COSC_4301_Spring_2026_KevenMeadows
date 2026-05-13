package org.example.neonarkintaketracker.cli;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

public class TableDisplay {
    // Create a display or print for list of all the creatures (GET api/creatures).
    public void displayCreatures(JsonNode JSON) {
        // Check if response is JSON array.
        if (!JSON.isArray()) {
            printGeneric(JSON); // Send to method to print.
            return;
        }

        borderLine(125); // start table border
        // establish header row
        System.out.printf(
                "%-5s %-15s %-25s %-15s %-15s %-25s%n", // column spacing
                "ID", // column header for creature ID.
                "Name", // column header for creature name.
                "Species", // column header for creature species.
                "Danger", // column header for creature danger level.
                "Status", // column header for creature status.
                "Habitat" // column header for creature habitat.
        );
        borderLine(125); // close header border.

        // loop through each creature for response.
        for (JsonNode creature : JSON) {
            System.out.printf(
                    "%-5s %-15s %-25s %-15s %-15s %-25s%n", // column spacing
                    value(creature,"id"), // add creature ID to column.
                    value(creature, "name"), // add creature name to column.
                    value(creature, "species"), // add creature species to column.
                    value(creature, "dangerLevel"), // add creature danger level to column.
                    value(creature, "status"), // add creature status to column.
                    value(creature, "habitatName") // add creature habitat to column.
            );
        }
        borderLine(125); // close the table border.
    }

    // Create method for displaying a single creature (GET api/creatures/{id}).
    public void displayOneCreature(JsonNode JSON){
        borderLine(140); // start table border
        System.out.printf(
                "%-5s %-15s %-20s %-15s %-15s %-25s %-40s%n", // column spacing
                "ID", // column header for creature ID.
                "Name", // column header for creature name.
                "Species", // column header for creature species.
                "Danger", // column header for creature danger level.
                "Status", // column header for creature status.
                "Habitat", // column header for creature habitat.
                "Notes" // column header for creature notes.
        );
        borderLine(140); // close header border
        System.out.printf(
                "%-5s %-15s %-20s %-15s %-15s %-25s %-40s%n", // column spacing
                value(JSON,"id"), // add creature ID to column.
                value(JSON, "name"), // add creature name to column.
                value(JSON, "species"), // add creature species to column.
                value(JSON, "dangerLevel"), // add creature danger level to column.
                value(JSON, "status"), // add creature status to column.
                value(JSON, "habitatName"), // add creature habitat to column.
                cutText(value(JSON, "notes"), 40)// add creature notes to column.
        );
        borderLine(140); // close table border
    }

    // Create method for renaming response (PUT api/creatures/{id}/name).
    public void displayRenameResponse(JsonNode JSON) {
        borderLine(100); // start table border
        System.out.printf(
                "%-15s %-20s %-20s %-25s%n", // column spacing
                "Creature ID", // column header for creature ID.
                "Old Creature Name", // column header for old creature name.
                "New Creature Name", // column header for new creature name.
                "Message" // column header for success message.
        );
        borderLine(100); // close header border
        System.out.printf(
                "%-15s %-20s %-20s %-25s%n", // column spacing
                value(JSON,"id"), // add creature ID to column.
                value(JSON, "oldName"), // add old creature name to column.
                value(JSON, "newName"), // add new creature name to column.
                value(JSON, "message") // add success message to column.
        );
        borderLine(100); // close table border
    }

    // Create method for removal response (DELETE api/creatures/{id}).
    public void displayRemoveResponse(JsonNode JSON) {
        borderLine(100); // start table border
        System.out.printf(
                "%-15s %-20s %-20s %-25s%n", // column spacing
                "Creature ID", // column header for creature ID.
                "Name", // column header for creature name.
                "Status", // column header for creature status.
                "Message" // column header for success message.
        );
        borderLine(100); // close header border
        System.out.printf(
                "%-15s %-20s %-20s %-25s%n", // column spacing
                value(JSON,"id"), // add creature ID to column.
                value(JSON, "name"), // add creature name to column.
                value(JSON, "status"), // add creature status to column.
                value(JSON, "message") // add success message to column.
        );
        borderLine(100); // close table border
    }

    // Create method for displaying observations (GET api/creatures/{id}/observations).
    public void displayObservations(JsonNode JSON) {
        // Get creature object and observations array.
        JsonNode creature = JSON.get("creature");
        JsonNode observations = JSON.get("observations");

        // print headers and values for creature and observations
        System.out.println();
        System.out.println("Creature");
        // check creature
        if (creature == null || creature.isNull()) {
            System.out.println("Creature information not returned.");
        }
        else {
            displayOneCreature(creature);
        }
        System.out.println();
        System.out.println("Observations");
        // account for if observations is empty
        if (observations == null || !observations.isArray() || observations.isEmpty()) {
            System.out.println("No observations have been made about this creature."); // empty observations
            return; // exit method
        }

        borderLine(140); // start border
        System.out.printf(
                "%-5s %-25s %-25s %-50s%n", // column spacing
                "ID", // column header for creature ID.
                "Observer Name", // column header for observer name.
                "Time Stamp", // column header for time stamp.
                "Note" // column header for creature notes.
        );
        borderLine(140); // close header border

        // loop through each creature observation.
        for (JsonNode observation : observations) {
            System.out.printf(
                    "%-5s %-25s %-25s %-50s%n", // column spacing
                    value(observation, "id"), // add creature ID to column.
                    value(observation, "observer"), // add creature name to column.
                    value(observation, "timestamp"), // add timestamp to column.
                    cutText(value(observation, "note"), 50) // add creature note to column.
            );
        }
        borderLine(140); // close table border
    }

    // Create method for getting feeding times (GET api/feedings?time=HH:MM).
    public void displayFeedings(JsonNode JSON) {
        System.out.println();
        System.out.println(value(JSON, "message")); // endpoint message

        // Get creatures array
        JsonNode creatures = JSON.get("creatures");

        // check if array is empty
        if (creatures == null || !creatures.isArray() || creatures.isEmpty()) {
            return; // return empty
        }

        borderLine(160); // start table border
        System.out.printf(
                "%-5s %-20s %-20s %-25s %-15s %-20s %-40s%n", // column spacing
                "ID", // column header for creature ID.
                "Name", // column header for creature name.
                "Species", // column header for creature species.
                "Habitat", // column header for creature habitat.
                "Status", // column header for creature status.
                "Food Type", // column header for food type.
                "Instructions" // column header for feeding instructions habitat.
        );
        borderLine(160); // close header border
        // loop through each creature for response.
        for (JsonNode creature : creatures) {
            System.out.printf(
                    "%-5s %-20s %-20s %-25s %-15s %-20s %-40s%n", // column spacing
                    value(creature,"id"), // add creature ID to column.
                    value(creature, "name"), // add creature name to column.
                    value(creature, "species"), // add creature species to column.
                    value(creature, "habitatName"), // add creature habitat to column.
                    value(creature, "status"), // add creature status to column.
                    value(creature, "foodType"), // add food type to column.
                    firstAvailable(creature, List.of("feedingInstructions", "instructions")) // add feeding instructions to column.
            );
        }
        borderLine(160); // close the table border.
    }

    // Create method for displaying the users (GET api/admin/users).
    public void displayUsers(JsonNode JSON) {
        // check if response is not JSON array
        if(!JSON.isArray()){
            printGeneric(JSON); // print generic response
            return;
        }

        borderLine(140); // start table border.
        System.out.printf(
                "%-5s %-20s %-30s %-30s %-15s %-20s%n", // column spacing
                "ID", // Header user ID.
                "Username", // Header username.
                "Full Name", // Header user full name.
                "Email", // Header user email.
                "Phone Number", // Header user phone number.
                "Role" // Header user role.
        );
        borderLine(140); // close header border.
        // loop through users.
        for (JsonNode user : JSON) {
            System.out.printf(
                    "%-5s %-20s %-30s %-30s %-15s %-20s%n", // column spacing
                    value(user,"id"), // add user ID to column.
                    value(user, "username"), // add username to column.
                    value(user, "fullName"), // add user full name to column.
                    value(user, "email"), // add user email to column.
                    firstAvailable(user, List.of("phoneNumber", "phone")), // add user phone number to column.
                    value(user, "role") // add user role to column.
            );
        }
        borderLine(140); // close table border.
    }

    // Create a method for API response errors.
    public void displayError(APIClient.APIResult error) {
        // get parsed JSON.
        JsonNode JSON = error.JSON();
        System.out.println();
        System.out.println("Request has failed."); // header
        System.out.println("HTTP Status Code: " + error.statusCode()); // print status code for error

        // check if error field exists.
        if (JSON.has("error")) {System.out.println("Error: " + value(JSON, "error"));}

        // check if message field exists.
        if (JSON.has("message")) {System.out.println("Message: " + value(JSON, "message"));}

        // check is non-normal error field exists.
        if (!JSON.has("error") && !JSON.has("message")) {
            System.out.println(error.originalBody());
        }
    }

    // Create helper function for table borders (----------)
    private void borderLine(int length) {
        // Create list for holding the dashes.
        List<String> dashes = new ArrayList<>();

        // For loop to add each dash to the list to make a borderline that is later printed.
        for (int i = 0; i < length; i++){
            dashes.add("-"); // add a dash
        }

        // Print borderline
        System.out.println(String.join("",dashes));
    }

    // Create method for printing JSON directly.
    public void printGeneric(JsonNode JSON){
        // Check if JSON is missing.
        if (JSON == null || JSON.isNull())
        {
            System.out.println("No data was returned."); // fallback message
            return;
        }
        // Print JSON that's formatted.
        System.out.println(JSON.toPrettyString());
    }

    // Create helper function for long text like from notes.
    private String cutText(String text, int maxLength) {
        // check text
        if (text == null) {return "";} // if empty return empty
        if (text.length() <= maxLength) {return text;} // if text fits return text.

        // cut text and add "..."
        return text.substring(0, maxLength - 3) + "...";
    }

    // Create method for getting values for table printing.
    private String value(JsonNode node, String field){
        // check field for null
        if (node == null || !node.has(field) || node.get(field).isNull()) {
            return ""; // return for empty
        }
        return node.get(field).asText(); // return if not empty
    }

    // Create method for feeding instructions.
    private String firstAvailable(JsonNode node, List<String> fields) {

        // loop through field names
        for (String field : fields) {
            // read current field
            String found = value(node, field);

            // check value and return if true
            if (!found.isBlank()) {
                return found;
            }
        }
        return ""; // return empty if no fields.
    }
}