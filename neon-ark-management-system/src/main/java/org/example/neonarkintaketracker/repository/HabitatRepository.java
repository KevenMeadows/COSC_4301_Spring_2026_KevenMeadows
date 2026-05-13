// HabitatRepository.java

// Updated package naming convention to match project.
package org.example.neonarkintaketracker.repository;

// Updated package naming convention to match project.
import org.example.neonarkintaketracker.entity.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repo for habitat entities, similar CRUD as creature repo
@Repository
public interface HabitatRepository extends JpaRepository<Habitat, Long> {
}