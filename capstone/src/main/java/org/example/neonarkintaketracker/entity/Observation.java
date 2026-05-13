package org.example.neonarkintaketracker.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data // for gets, sets etc.
@NoArgsConstructor // makes empty constructor.
@AllArgsConstructor // constructor with all fields.
@Builder // for object creation.
@Entity // class is database entity.
@Table(name = "observations") // link to observations table.
public class Observation {
    @Id // Set as primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Store observation id.

    @ManyToOne(optional = false)
    @JoinColumn(name = "creature_id", nullable = false) // Connects tables
    private Creature creature; // Store the creature that the observation is about.

    @Column(name = "observer_name", nullable = false, length = 100) // mapping for name to variable
    private String observerName; // Stores the name of the observer.

    @Column(name = "note_text", nullable = false, length = 300) // mapping for note to variable
    private String noteText; // Stores observation note.

    @Column(name = "observed_at", nullable = false) // mapping time to variable
    private LocalDateTime observedAt; // Stores timestamp for when the observation was.
}
