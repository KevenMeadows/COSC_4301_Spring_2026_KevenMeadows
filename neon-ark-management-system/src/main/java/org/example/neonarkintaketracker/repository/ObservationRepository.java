package org.example.neonarkintaketracker.repository;
import org.example.neonarkintaketracker.entity.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repo for observation storage.
@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {

    //  Get all observations for a creature sorted oldest to newest.
    List<Observation> findByCreatureIdOrderByObservedAtAsc(Long creatureId);
}
