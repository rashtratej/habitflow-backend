package com.rashtratej.habitTrackerVersion1.controller;

import com.rashtratej.habitTrackerVersion1.dto.CreateHabitRequest;
import com.rashtratej.habitTrackerVersion1.entity.Habit;
import com.rashtratej.habitTrackerVersion1.service.HabitService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habits")
public class HabitController {

    private final HabitService habitService;

    public HabitController(
            HabitService habitService
    ) {
        this.habitService = habitService;
    }

    @PostMapping("/create")
    public ResponseEntity<Habit> createHabit(@Valid @RequestBody CreateHabitRequest request) {

        Habit savedHabit =
                habitService.createHabit(request);

        return ResponseEntity.ok(savedHabit);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Habit>> getMyHabits() {

        List<Habit> habits =
                habitService.getMyHabits();

        return ResponseEntity.ok(habits);
    }

    @PatchMapping("/{id}/complete") public ResponseEntity<Habit> completeHabit(@PathVariable Long id
    ) {

        Habit updatedHabit =
                habitService
                        .markHabitComplete(id);

        return ResponseEntity.ok(
                updatedHabit
        );
    }
}