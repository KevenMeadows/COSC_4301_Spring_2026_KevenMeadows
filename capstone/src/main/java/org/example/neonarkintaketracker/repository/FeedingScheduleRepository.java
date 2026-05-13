package org.example.neonarkintaketracker.repository;
import org.example.neonarkintaketracker.entity.FeedingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalTime;
import java.util.List;

public interface FeedingScheduleRepository extends JpaRepository<FeedingSchedule, Long> {
    // get all active schedules.
    List<FeedingSchedule> findByFeedingTimeAndActiveTrue(LocalTime feedingTime);
}