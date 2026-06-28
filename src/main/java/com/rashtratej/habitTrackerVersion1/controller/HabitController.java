package com.rashtratej.habitTrackerVersion1.controller;

import com.rashtratej.habitTrackerVersion1.dto.*;
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

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @PostMapping("/create")
    public ResponseEntity<HabitResponse> createHabit(@Valid @RequestBody CreateHabitRequest request) {

        HabitResponse savedHabit =
                habitService.createHabit(request);

        return ResponseEntity.ok(savedHabit);
    }

    @GetMapping("/my")
    public ResponseEntity<PaginatedHabitResponse> getMyHabits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        PaginatedHabitResponse habits = habitService.getMyHabits(page, size, sortBy, direction);

        return ResponseEntity.ok(habits);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<HabitResponse> completeHabit(@PathVariable Long id) {

        HabitResponse updatedHabit =
                habitService
                        .markHabitComplete(id);

        return ResponseEntity.ok(
                updatedHabit
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHabit(@PathVariable Long id) {

        String response =
                habitService
                        .deleteHabit(id);

        return ResponseEntity.ok(
                response
        );
    }

    @GetMapping("/stats")
    public ResponseEntity<HabitStatsResponse> getHabitStats() {

        HabitStatsResponse stats =
                habitService.getHabitStats();

        return ResponseEntity.ok(stats);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitResponse> updateHabit(@PathVariable Long id, @Valid @RequestBody UpdateHabitRequest request) {

        HabitResponse updatedHabit =
                habitService.updateHabit(
                        id,
                        request
                );

        return ResponseEntity.ok(
                updatedHabit
        );
    }

}