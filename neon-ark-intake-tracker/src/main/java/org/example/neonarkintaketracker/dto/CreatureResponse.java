package org.example.neonarkintaketracker.dto;

import java.time.LocalDateTime;

/**
 * DTO used for READ responses going back to the Java CLI client.
 * NOTE:
 *  - We INCLUDE id and createdAt because the server/database controls them.
 *  - This is the "allowed" shape of outgoing data.
 *  - The client can see these values, but should never be able to set them.
 */
public record CreatureResponse(
        Long id,
        String name,
        String species,
        String dangerLevel,
        String condition,
        String notes,
        LocalDateTime createdAt, // Changed to LocalDateTime, better practice than java reference.
        Long habitatId // Added to be referenced for creating a creature in service class.
) {}