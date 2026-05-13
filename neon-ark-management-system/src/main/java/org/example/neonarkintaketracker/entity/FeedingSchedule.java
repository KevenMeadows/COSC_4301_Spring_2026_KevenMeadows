package org.example.neonarkintaketracker.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Data // for gets, sets etc.
@NoArgsConstructor // makes empty constructor.
@AllArgsConstructor // constructor with all fields.
@Builder // for object creation.
@Entity // class is database entity.
@Table(name = "feeding_schedules") // Link to feeding_schedules table.
public class FeedingSchedule {

    @Id // Set as primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Stores feeding schedule id.

    @ManyToOne(optional = false) // creature can have multiple feeding schedules.
    @JoinColumn(name = "creature_id", nullable = false) // Connects creature id
    private Creature creature; // Store the creature the feeding schedule is for.

    @Column(name = "feeding_time", nullable = false) // Mapping for feeding time
    private LocalTime feedingTime; // Store time of day feeding should happen.

    @Column(name = "food_type", nullable = false, length = 100) // Mapping for type of food.
    private String foodType; // Store food the creature should get.

    @Column(name = "feeding_instructions", columnDefinition = "TEXT") // In case instructions are long.
    private String feedingInstructions; // Stores any feeding instructions.

    @Column(nullable = false) // map active or not.
    private Boolean active; // Tells if schedule is active or not.
}