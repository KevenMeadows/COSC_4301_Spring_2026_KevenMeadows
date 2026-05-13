package org.example.neonarkintaketracker.dto;

public record RenameCreatureResponse (
        Long id,
        String oldName,
        String newName,
        String message
) {}
