package org.example.neonarkintaketracker.dto;
import java.util.List;

public record CreatureObservationResponse (
        CreatureResponse creature,
        List<ObservationResponse> observations
){}
