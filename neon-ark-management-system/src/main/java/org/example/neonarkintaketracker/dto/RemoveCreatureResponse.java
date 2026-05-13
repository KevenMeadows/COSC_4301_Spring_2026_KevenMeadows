package org.example.neonarkintaketracker.dto;

public record RemoveCreatureResponse (
        Long id,
        String name,
        String status,
        String message
) {}