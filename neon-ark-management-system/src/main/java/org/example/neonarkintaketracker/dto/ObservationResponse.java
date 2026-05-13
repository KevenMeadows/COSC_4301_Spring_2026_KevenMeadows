package org.example.neonarkintaketracker.dto;
import java.time.LocalDateTime;

public record ObservationResponse (
            Long id,
            String observer,
            String note,
            LocalDateTime timestamp
){}
