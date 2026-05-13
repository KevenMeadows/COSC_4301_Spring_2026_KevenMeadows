package org.example.neonarkintaketracker.dto;

// Add these imports to be able to give back message response for null entries.
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO used for CREATE and UPDATE requests coming from the Java CLI client.
 * NOTE:
 *  - We intentionally EXCLUDE id and createdAt because those are DB-managed.
 *  - This is the "allowed" shape of incoming data.
 */
public record CreatureRequest(
        // no Long id and no LocalDateTime createdAt
        @NotBlank(message = "Name is required.") // Response for empty string
        @Size(max = 120, message = "Name cannot exceed 120 characters.") // Size limit
        String name,

        @NotBlank(message = "Species is required.") // Response for empty string
        @Size(max = 120, message = "Name cannot exceed 120 characters.") // Size limit
        String species,

        @NotBlank(message = "Danger level is required.") // Response for empty string
        String dangerLevel,

        @NotBlank(message = "Status is required.") // Response for empty string
        String status,
        String notes,

        @NotNull(message = "Habitat ID is required.") // Response for null
        Long habitatId // Added to be referenced for creating a creature in service class.
) {}