// CreatureController.java

package org.example.neonarkintaketracker.controller;

// Updated package naming convention to match project.
import jakarta.validation.Valid;
// import org.example.neonarkintaketracker.entity.Creature;
import org.example.neonarkintaketracker.dto.CreatureRequest;
import org.example.neonarkintaketracker.dto.CreatureResponse;
import org.example.neonarkintaketracker.dto.RenameCreatureRequest;
import org.example.neonarkintaketracker.dto.RenameCreatureResponse;
import org.example.neonarkintaketracker.dto.RemoveCreatureResponse;
import org.example.neonarkintaketracker.dto.CreatureObservationResponse;
import org.example.neonarkintaketracker.service.CreatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// import java.util.Optional; // added per instruction

/*
 * This controller handles incoming HTTP requests for /api/creatures
 */
@RestController
@RequestMapping("/api/creatures")
public class CreatureController {

    private final CreatureService service;

    // Constructor-based Dependency Injection (DI)
    public CreatureController(CreatureService service) {
        this.service = service;
    }

    /*
     * Map HTTP GET requests at /api/creatures to this method
     * Example: GET http://localhost:8080/api/creatures
     */
    @GetMapping
    public ResponseEntity<List<CreatureResponse>> getAllCreatures() {

        // List<Creature> creatures = service.getAllCreatures();
        // Return 200 OK with JSON body
        // return ResponseEntity.ok(creatures);
        // In order to use DTOs through service, controller needs to be updated to reference objects properly.
        return ResponseEntity.ok(service.getAllCreaturesResponse());
    }

    // added per instruction
    // NEW: GET /api/creatures/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CreatureResponse> getCreatureById(@PathVariable Long id) {

        // Optional<Creature> maybeCreature = service.getCreatureById(id);
        // if (maybeCreature.isEmpty()) {
            // 404 when id does not exist
           // return ResponseEntity.notFound().build();
        //}
        // 200 OK when found
        // return ResponseEntity.ok(maybeCreature.get());

        // Fix to allow DTOs to be referenced through service. The way above doesn't actually use the DTOs.
        return ResponseEntity.ok(service.getCreatureByIdResponse(id));

    }

    // Create method that makes new creature
    // Endpoint is POST /api/creatures
    // Request to DTO then return created creature.
    @PostMapping
    public ResponseEntity<CreatureResponse> createCreature(@Valid @RequestBody CreatureRequest request) {
        CreatureResponse response = service.createCreature(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Add observations method.
    @GetMapping("/{id}/observations")
    public ResponseEntity<CreatureObservationResponse> getCreatureObservations(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCreatureObservations(id));
    }

    // Mapping for renaming the creature.
    @PutMapping("/{id}/name")
    public ResponseEntity<RenameCreatureResponse> RenameCreature(
            @PathVariable Long id,
            @Valid @RequestBody RenameCreatureRequest request
            ) {return ResponseEntity.ok(service.RenameCreature(id, request));}

    // Use delete mapping for remove creature.
    @DeleteMapping("/{id}")
    public ResponseEntity<RemoveCreatureResponse> RemoveCreature(
            @PathVariable Long id) {return ResponseEntity.ok(service.RemoveCreature(id));}
}
