// CreatureService.java

// Updated package naming convention to match project.
package org.example.neonarkintaketracker.service;
import org.example.neonarkintaketracker.dto.*;
import org.example.neonarkintaketracker.entity.Creature;
import org.example.neonarkintaketracker.entity.Habitat; // add habitat for service request reference
// don't need, can reference this through repository // import org.example.neonarkintaketracker.entity.Observation; // add observation entity
import org.example.neonarkintaketracker.exception.BadRequestException;
import org.example.neonarkintaketracker.exception.DuplicateCreatureException;
import org.example.neonarkintaketracker.exception.NotFoundException;
import org.example.neonarkintaketracker.repository.CreatureRepository;
import org.example.neonarkintaketracker.repository.HabitatRepository; // added repo for habitats
import org.example.neonarkintaketracker.repository.ObservationRepository; // added repo for observations
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // for time reference variable createdAt
import java.util.*;
// removing for easier use of objects and mapping - import java.util.Optional; // added per instruction

/*
 * Thin service for now.
 * Keeps the controller clean and gives us a place to add
 * validation, DTO mapping, and business rules later.
 */
@Service
public class CreatureService {

    private final CreatureRepository creatureRepository; // Update all repository variables to creatureRepository to
    // distinguish repositories.

    // Need to create a habitat repository for reference here.
    private final HabitatRepository habitatRepository;

    // Need to create a observation repository for reference here.
    private final ObservationRepository observationRepository;

    // Constructor
    public CreatureService(
            CreatureRepository creatureRepository,
            HabitatRepository habitatRepository,
            ObservationRepository observationRepository
    )
    {
        this.creatureRepository = creatureRepository;
        this.habitatRepository = habitatRepository; // Added to connect repo habitat lookup.
        this.observationRepository = observationRepository;
    }

    /*
     * Return every creature currently in the database.
     * This is the "Read" operation for GET /api/creatures
     */
    // Added Response at end of method for less confusion in controller and service files.
    public List<CreatureResponse> getAllCreaturesResponse() {
        return creatureRepository.findAll().stream().map(this::mapToResponse).toList(); // Alter creature List to go through DTO
    }

    // added per instruction, added Response to end of method for less confusion in controller and service files.
    // NEW: Return one creature by id (Optional = may not exist)
    public CreatureResponse getCreatureByIdResponse(Long id) {
        // create creature object for reference and mapping to use exception handling methods.
        Creature creature = creatureRepository.findById(id).orElseThrow(() -> new NotFoundException("Creature ID " + id + " was not found."));

        // return creature object through mapping.
        return mapToResponse(creature);
    }

    // Create a createCreature method that uses request DTO and creates creature.
    // things to note:
    // - exception for no habitat that exists, no duplicates and bad requests.
    // - Need to map DTO to creature and make sure to save creature
    // - Connect to response using mapping.
    public CreatureResponse createCreature(CreatureRequest request) {
        // ensure valid values with created helper function
        ValidateCreatureValues(request.dangerLevel(), request.status());
        // Need to create a habitat repository for reference here.
        // Create that in same folder as CreatureRepository. Add new exception.
        Habitat habitat = habitatRepository.findById(request.habitatId()).orElseThrow(() -> new BadRequestException(
                "Habitat not found. Habitat Id: " + request.habitatId()
        ));

        // create check for duplicates using method
        if (creatureRepository.existsByHabitatIdAndNameIgnoreCase(request.habitatId(), request.name())) {
            throw new DuplicateCreatureException(
                    "Creature already exists in this habitat: '" + request.name()
            );
        }

        // Mapping, use setName, setSpecies etc. for DTO request.
        Creature creature = new Creature();
        creature.setName(request.name());
        creature.setSpecies(request.species());
        creature.setDangerLevel(request.dangerLevel().toUpperCase());
        creature.setStatus(request.status().toUpperCase());
        creature.setNotes(request.notes());
        creature.setCreatedAt(LocalDateTime.now());
        creature.setUpdatedAt(LocalDateTime.now());
        creature.setHabitat(habitat);

        // Save creature and map for return to response.
        Creature savedCreature = creatureRepository.save(creature);
        return mapToResponse(savedCreature);
    }

    // Add observations response method.
    public CreatureObservationResponse getCreatureObservations(Long id) {
        // creature object to search for creature
        Creature creature = creatureRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Creature with the " + id + " does not exist."));

        // Query observations table.
        List<ObservationResponse> observations = observationRepository
                .findByCreatureIdOrderByObservedAtAsc(id).stream()
                .map(observation -> new ObservationResponse(
                        observation.getId(), // get id
                        observation.getObserverName(), // get observer
                        observation.getNoteText(), // get note
                        observation.getObservedAt() // timestamp
                ))
                .toList(); // Converts into list

        // return the response.
        return new CreatureObservationResponse(
                mapToResponse(creature),
                observations
        );
    }

    // Create method for renaming creature with response that was created.
    public RenameCreatureResponse RenameCreature(Long id, RenameCreatureRequest request) {
        // create object for creature
        Creature creature = creatureRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Creature with the " + id + " does not exist."));

        // store names into variables by retrieving name and referencing new name
        String oldName = creature.getName();
        String newName = request.newName();

        // If the names are the same, throw an exception.
        if (oldName.equalsIgnoreCase(newName)) {
            throw new BadRequestException("New name given is the current name.");
        }

        // Check if there is a duplicate with that name.
        if (creatureRepository.existsByHabitatIdAndNameIgnoreCase(creature.getHabitat().getId(), newName)) {
            throw new DuplicateCreatureException(
                    "Creature with the name " + newName + " already exists in habitat."
            );
        }

        // if passes, set the name to the new name, update the updatedAt time
        creature.setName(newName);
        creature.setUpdatedAt(LocalDateTime.now());

        // Save creature change to repo
        creatureRepository.save(creature);

        // Reference the Rename response to inform creature was renamed
        return new RenameCreatureResponse(
                creature.getId(),
                oldName,
                newName,
                "Creature has been renamed."
        );
    }

    // Create a way to remove a creature.
    public RemoveCreatureResponse RemoveCreature(Long id) {
        // create object for creature
        Creature creature = creatureRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Creature with the " + id + " does not exist."));

        // Assign removal status and update time for when it happened.
        creature.setStatus("REMOVED");
        creature.setUpdatedAt(LocalDateTime.now());

        // Save for creature change
        creatureRepository.save(creature);

        // Use remove response to let user know creature was removed.
        return new RemoveCreatureResponse(
                creature.getId(),
                creature.getName(),
                creature.getStatus(),
                "Creature has been removed."
        );
    }

    // Create mapping for response that is used by return in created creature method.
    // This will convert creature entity into the DTO CreatureResponse for use.
    private CreatureResponse mapToResponse(Creature creature) {
        // Create object for response and return with info.
        return new CreatureResponse(
                creature.getId(),
                creature.getName(),
                creature.getSpecies(),
                creature.getDangerLevel(),
                creature.getStatus(),
                creature.getNotes(),
                creature.getCreatedAt(),
                creature.getUpdatedAt(), // added for updating timestamps
                creature.getHabitat().getId(),
                creature.getHabitat().getLocation() // added for showing name of habitat
        );
    }

    // ensure values for creature status and danger level are consistent.
    private void ValidateCreatureValues(String dangerlevel, String status) {
        // Normalize data for consistency
        String normDanger = dangerlevel.trim().toUpperCase();
        String normStatus = status.trim().toUpperCase();

        // Ensure entered info is allowed for both danger level and status
        if (!List.of("LOW", "MEDIUM", "HIGH", "EXTREME").contains(normDanger)) {
            throw new BadRequestException("Danger level invalid. Danger Levels Allowed: LOW, MEDIUM, HIGH, or EXTREME.");
        }
        if (!List.of("ACTIVE", "STABLE", "QUARANTINED", "CRITICAL", "AGGRESSIVE", "DORMANT", "REMOVED")
                .contains(normStatus)) {
            throw new BadRequestException(
                    "Status invalid. Statuses Allowed: ACTIVE, STABLE, QUARANTINED, CRITICAL, AGGRESSIVE, DORMANT, or REMOVED."
            );
        }
    }
}
