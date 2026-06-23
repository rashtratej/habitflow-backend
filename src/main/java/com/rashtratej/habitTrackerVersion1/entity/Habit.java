package com.rashtratej.habitTrackerVersion1.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "habits")
@Data
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;
}
