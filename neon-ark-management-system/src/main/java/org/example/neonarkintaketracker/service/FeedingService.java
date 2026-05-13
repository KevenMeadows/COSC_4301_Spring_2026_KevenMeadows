package org.example.neonarkintaketracker.service;
import org.example.neonarkintaketracker.dto.FeedingCreatureResponse;
import org.example.neonarkintaketracker.dto.FeedingResponse;
import org.example.neonarkintaketracker.entity.FeedingSchedule;
import org.example.neonarkintaketracker.repository.FeedingScheduleRepository;
import org.example.neonarkintaketracker.exception.BadRequestException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Service
public class FeedingService {
    // store repo
    private final FeedingScheduleRepository feedingScheduleRepository;

    // constructor
    public FeedingService(FeedingScheduleRepository feedingScheduleRepository){
        this.feedingScheduleRepository = feedingScheduleRepository;
    }

    // get method
    public FeedingResponse getFeedingsByTime(String timeTemp) {
        // check to make sure time exists
        if (timeTemp == null || !timeTemp.matches("^\\d{2}:\\d{2}$")){
            throw new BadRequestException("Time is required. Format: HH:MM");
        }

        // time variable
        LocalTime time;

        // try catch for parsing
        try {
            time = LocalTime.parse(timeTemp); // convert to correct format.
        }
        catch (DateTimeParseException exception) {
            throw new BadRequestException("Time is invalid. Use the format HH:MM.");
        }

        // Query database
        List<FeedingCreatureResponse> creatures = feedingScheduleRepository
                .findByFeedingTimeAndActiveTrue(time) // searches for active feeding with input time.
                .stream() // convert to filter and map
                .filter(feed -> !"REMOVED".equalsIgnoreCase(feed.getCreature().getStatus())) // filter out any removed creatures
                .map(this::mapToResponse) // convert for DTO
                .toList(); // back to list

        // check to see if empty
        String message = creatures.isEmpty()
                ? "No creatures need to be fed at " + timeTemp + "."
                : "Creatures that need to be fed at " + timeTemp + ".";
        return new FeedingResponse(message,timeTemp, creatures);
    }

    // convert entity to DTO
    private FeedingCreatureResponse mapToResponse(FeedingSchedule schedule){
        // Response object
        return new FeedingCreatureResponse(
                schedule.getCreature().getId(), // creature id
                schedule.getCreature().getName(), // creature name
                schedule.getCreature().getSpecies(), // creature species
                schedule.getCreature().getStatus(), // creature status
                schedule.getCreature().getHabitat().getLocation(), // habitat name
                schedule.getFoodType(), // what type of food for feeding
                schedule.getFeedingInstructions() // instructions for feeding
        );
    }
}
