package org.example.neonarkintaketracker.controller;
import org.example.neonarkintaketracker.dto.FeedingResponse;
import org.example.neonarkintaketracker.service.FeedingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // copy from CreatureController
@RequestMapping("/api/feedings") // change to align with correct url
public class FeedingController {

    // store service dependency
    private final FeedingService feedingService;

    // constructor
    public FeedingController(FeedingService feedingService) {this.feedingService = feedingService;}

    // get request handle
    @GetMapping
    public ResponseEntity<FeedingResponse> getFeedingsByTime(@RequestParam String time) {
        // read the time query and return 200 OK with feedings
        return ResponseEntity.ok(feedingService.getFeedingsByTime(time));
    }
}
