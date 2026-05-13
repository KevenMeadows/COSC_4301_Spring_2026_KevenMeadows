// CreatureRepository.java

// Updated package naming convention to match project.
package org.example.neonarkintaketracker.repository;

// Updated package naming convention to match project.
import org.example.neonarkintaketracker.entity.Creature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Simple CRUD + paging/sorting out of the box
@Repository
public interface CreatureRepository extends JpaRepository<Creature, Long> {
    // Duplicate check support
    boolean existsByHabitatIdAndNameIgnoreCase(Long habitatId, String name);
}