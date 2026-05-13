package org.example.neonarkintaketracker.dto;

// Add these imports to be able to give back message response for null entries.
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RenameCreatureRequest (
        @NotBlank(message = "Name is required.") // Response for empty string
        @Size(max = 120, message = "Name cannot exceed 120 characters.") // Size limit
        String newName
) {}
