package org.example.neonarkintaketracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "creatures",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "chk_duplicate",
                        columnNames = {"habitat_id", "name"}
                )
        }
)

public class Creature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 120)
    private String species;

    @Column(name = "danger_level", nullable = false, length = 30)
    private String dangerLevel;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // For when anything is updated timestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Many Creatures -> One Habitat
    @ManyToOne(optional = false)
    @JoinColumn(name = "habitat_id", nullable = false)
    private Habitat habitat;
}