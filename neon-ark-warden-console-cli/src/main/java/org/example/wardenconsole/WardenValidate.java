package org.example.wardenconsole;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

public class WardenValidate {
    // store roles
    private static final Set<String> roles = Set.of("Admin", "Field", "Rift", "Trainer", "Astral");
    // store status of employment
    private static final Set<String> statuses = Set.of("Active", "OnLeave", "Terminated");
    // store clearance levels
    private static final Set<String> clearances = Set.of("Alpha", "Omega", "Eclipse");
    // store identifiers
    private static final Set<String> identifiers = Set.of("BADGE", "PASSPORT", "VISA");

    // Check missing value.
    public boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    // Check if date is valid.
    public boolean checkDate(String value) {
        if(isBlank(value)) {
            return false;
        }

        // try exception
        try {
            LocalDate.parse(value); // Parse date YYYY-MM-DD
            return value.matches("^\\d{4}-\\d{2}-\\d{2}$");
        }
        catch (DateTimeParseException exception){
            return false; // invalid date return
        }

    }

    // validate option date
    public boolean isOptionalDate(String value) {
        if (isBlank(value)) {
            return true;
        }
        return checkDate(value);
    }

    // Check email
    public boolean checkEmail(String email) {
        // require typical email format.
        return !isBlank(email) && email.contains("@") && email.contains(".");
    }

    // check role
    public boolean checkRole(String role) {
        // return true for roles that exist.
        return roles.contains(role);
    }

    // check employment status.
    public boolean checkStatus(String status) {
        // return true for valid statuses.
        return statuses.contains(status);
    }

    // Check clearance level.
    public boolean checkClearance(String clearance) {
        // return true for valid clearance levels.
        return clearances.contains(clearance);
    }

    // Check identifier types.
    public boolean checkIdentifier(String identity) {
        // return true for valid identifiers.
        return identifiers.contains(identity);
    }

    // Check if warden ID exists.
    public boolean wardenIdExists(List<Warden> wardens, int wardenId) {
        // return true if ID already exists.
        return wardens.stream().anyMatch(warden -> warden.getWardenId() == wardenId);
    }

    // Check if email exists.
    public boolean emailExists(List<Warden> wardens, String email) {
        return wardens.stream().anyMatch(warden -> warden.getEmail().equalsIgnoreCase(email));
    }

    // Check if identifier exists.
    public boolean identifierExists(List<Warden> wardens, String type, String value) {
        return wardens.stream().anyMatch(warden -> warden.getIdentifierType().equalsIgnoreCase(type)
                && warden.getIdentifierValue().equalsIgnoreCase(value));
    }
}
