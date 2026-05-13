package org.example.neonarkintaketracker.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data // for gets, sets etc.
@NoArgsConstructor // makes empty constructor.
@AllArgsConstructor // constructor with all fields.
@Builder // for object creation.
@Entity // class is database entity.
@Table(name = "system_users") // Link to system_users table.
public class SystemUser {

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto gen by database.
    private Long id; // store ID

    @Column(nullable = false, unique = true, length = 100) // username, unique so only one of with username name can exist.
    private String username; // store username

    @Column(name = "full_name", nullable = false, length = 100) // map full name to column
    private String fullName; // store user's name

    @Column(nullable = false, length = 120) // map email
    private String email; // store user email.

    @Column(name = "phone_number", length = 20) // map phone number
    private String phoneNumber; // store user phone number.

    @Column(nullable = false, length = 50) // map roles
    private String role; // user system role

    @Column(nullable = false)
    private Boolean active; // record of active users

    @Column(name = "created_at", nullable = false) // map time
    private LocalDateTime createdAt; // time storage


}
