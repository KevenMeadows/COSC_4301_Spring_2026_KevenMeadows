package org.example.neonarkintaketracker.dto;
import java.util.List;

public record FeedingResponse (
        String message,
        String time,
        List<FeedingCreatureResponse> creatures // which need feeding
) {}
