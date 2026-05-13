package org.example.neonarkintaketracker.dto;

// DTO for get admin/users
public record AdminUserResponse (
        Long id, // user id
        String username, // username
        String fullName, // user's name
        String email, // user's email
        String phoneNumber, // user's contact
        String role // user's role
) {}
