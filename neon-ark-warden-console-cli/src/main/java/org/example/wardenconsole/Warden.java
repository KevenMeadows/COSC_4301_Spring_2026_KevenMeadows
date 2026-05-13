package org.example.wardenconsole;

public class Warden {
    private final int wardenId; // store warden id
    private final String firstName; // store first name
    private final String lastName; // store last name
    private final String identifierType; // store identifier type
    private final String identifierValue; // store identifier value
    private final String email; // store email
    private final String role; // store role
    private final String employmentStatus; // store status of employment
    private final String clearanceLevel; // store clearance level
    private final String startDate; // store start date
    private final String endDate; // store end date

    // Constructor
    public Warden (
            int wardenId,
            String firstName,
            String lastName,
            String identifierType,
            String identifierValue,
            String email,
            String role,
            String employmentStatus,
            String clearanceLevel,
            String startDate,
            String endDate
    ) {
        this.wardenId = wardenId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identifierType = identifierType;
        this.identifierValue = identifierValue;
        this.email = email;
        this.role = role;
        this.employmentStatus = employmentStatus;
        this.clearanceLevel = clearanceLevel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Get Warden ID.
    public int getWardenId() {
        return wardenId; // Return Warden ID.
    }
    // Get first name.
    public String getFirstName() {
        return firstName; // Return first name.
    }
    // Get last name.
    public String getLastName() {
        return lastName; // Return last name.
    }
    // Get identifier type.
    public String getIdentifierType() {
        return identifierType; // Return identifier type.
    }
    // Get identifier value
    public String getIdentifierValue() {
        return identifierValue; // Return identifier value.
    }
    // Get email.
    public String getEmail() {
        return email; // Return email.
    }
    // Get role.
    public String getRole() {
        return role; // Return role.
    }
    // Get employment status.
    public String getEmploymentStatus() {
        return employmentStatus; // Return employment status.
    }
    // Get clearance level.
    public String getClearanceLevel() {
        return clearanceLevel; // Return clearance level.
    }
    // Get start date.
    public String getStartDate() {
        return startDate; // Return start date.
    }
    // Get end date.
    public String getEndDate() {
        return endDate; // Return end date.
    }
}
