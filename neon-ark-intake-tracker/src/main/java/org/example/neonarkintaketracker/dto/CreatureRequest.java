package org.example.neonarkintaketracker.dto;
/**
 * DTO used for CREATE and UPDATE requests coming from the Java CLI client.
 * NOTE:
 *  - We intentionally EXCLUDE id and createdAt because those are DB-managed.
 *  - This is the "allowed" shape of incoming data.
 */
public record CreatureRequest(
        // no Long id and no LocalDateTime createdAt
        String name,
        String species,
        String dangerLevel,
        String condition,
        String notes,
        Long habitatId // Added to be referenced for creating a creature in service class.
) {}