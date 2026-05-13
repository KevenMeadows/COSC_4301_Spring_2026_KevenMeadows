package org.example.neonarkintaketracker.repository;
import org.example.neonarkintaketracker.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

// Left empty, JpaRepository does find all.
public interface SystemUserRepository extends JpaRepository<SystemUser, Long>{
}
