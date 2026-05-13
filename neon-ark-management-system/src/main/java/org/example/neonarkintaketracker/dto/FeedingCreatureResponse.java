package org.example.neonarkintaketracker.dto;

public record FeedingCreatureResponse (
        Long id,
        String name,
        String species,
        String status,
        String habitatName,
        String foodType,
        String feedingInstructions
) {}
