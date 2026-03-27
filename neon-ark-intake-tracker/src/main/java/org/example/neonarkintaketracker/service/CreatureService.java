// CreatureService.java

// Updated package naming convention to match project.
package org.example.neonarkintaketracker.service;
import org.example.neonarkintaketracker.dto.CreatureRequest; // added to complete service from request.
import org.example.neonarkintaketracker.dto.CreatureResponse; // added to complete service class for when it is requested.
import org.example.neonarkintaketracker.entity.Creature;
import org.example.neonarkintaketracker.entity.Habitat; // add habitat for service request reference
import org.example.neonarkintaketracker.repository.CreatureRepository;
import org.example.neonarkintaketracker.repository.HabitatRepository; // added repo for habitat
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // for time reference variable createdAt
import java.util.List;
import java.util.Optional; // added per instruction

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

    //
    public CreatureService(CreatureRepository creatureRepository, HabitatRepository habitatRepository)
    {
        this.creatureRepository = creatureRepository;
        this.habitatRepository = habitatRepository; // Added to connect repo habitat lookup.
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
    public Optional<CreatureResponse> getCreatureByIdResponse(Long id) {
        // similar changes from List change to make sure DTO is being used.
        return creatureRepository.findById(id).map(this::mapToResponse);
    }

    // Create a createCreature method that uses request DTO and creates creature.
    // things to note:
    // - exception for no habitat that exists
    // - Need to map DTO to creature and make sure to save creature
    // - Connect to response using mapping.
    public CreatureResponse createCreature(CreatureRequest request) {
        // Need to create a habitat repository for reference here.
        // Create that in same folder as CreatureRepository.
        Habitat habitat = habitatRepository.findById(request.habitatId()).orElseThrow(() -> new RuntimeException(
                "Habitat not found. Habitat Id: " + request.habitatId()
        ));

        // Mapping, use setName, setSpecies etc. for DTO request.
        Creature creature = new Creature();
        creature.setName(request.name());
        creature.setSpecies(request.species());
        creature.setDangerLevel(request.dangerLevel());
        creature.setCondition(request.condition());
        creature.setNotes(request.notes());
        creature.setCreatedAt(LocalDateTime.now());
        creature.setHabitat(habitat);

        // Save creature and map for return to response.
        Creature savedCreature = creatureRepository.save(creature);
        return mapToResponse(savedCreature);
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
                creature.getCondition(),
                creature.getNotes(),
                creature.getCreatedAt(),
                creature.getHabitat().getId()
        );
    }
}
