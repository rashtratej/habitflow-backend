package com.rashtratej.habitTrackerVersion1.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users") // Avoids database keyword conflict with "user"

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String profilePictureUrl;

    private LocalDateTime createdAt;




}
