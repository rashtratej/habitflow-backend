package com.rashtratej.habitTrackerVersion1.controller;

import com.rashtratej.habitTrackerVersion1.dto.*;
import com.rashtratej.habitTrackerVersion1.entity.Habit;
import com.rashtratej.habitTrackerVersion1.service.HabitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habits")
@Tag(
        name = "Habit Management",
        description = "Create, update, delete and manage habits"
)
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }
    @Operation(
            summary = "Create new habit",
            description = "Creates a new habit for authenticated user"
    )
    @PostMapping("/create")
    public ResponseEntity<HabitResponse> createHabit(@Valid @RequestBody CreateHabitRequest request) {

        HabitResponse savedHabit =
                habitService.createHabit(request);

        return ResponseEntity.ok(savedHabit);
    }

    @Operation(
            summary = "Get all user habits"
    )
    @GetMapping("/my")
    public ResponseEntity<PaginatedHabitResponse> getMyHabits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        PaginatedHabitResponse habits = habitService.getMyHabits(page, size, sortBy, direction);

        return ResponseEntity.ok(habits);
    }

    @Operation(
            summary = "Mark habit complete"
            // , description = "Creates a new habit for authenticated user"
    )
    @PatchMapping("/{id}/complete")
    public ResponseEntity<HabitResponse> completeHabit(@PathVariable Long id) {

        HabitResponse updatedHabit =
                habitService
                        .markHabitComplete(id);

        return ResponseEntity.ok(
                updatedHabit
        );
    }

    @Operation(
            summary = "delete habit"
           // ,description = "Creates a new habit for authenticated user"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHabit(@PathVariable Long id) {

        String response =
                habitService
                        .deleteHabit(id);

        return ResponseEntity.ok(
                response
        );
    }

    @Operation(
            summary = "Get habit statistics"
            // , description = "Creates a new habit for authenticated user"
    )
    @GetMapping("/stats")
    public ResponseEntity<HabitStatsResponse> getHabitStats() {

        HabitStatsResponse stats =
                habitService.getHabitStats();

        return ResponseEntity.ok(stats);
    }

    @Operation(
            summary = "Update habit"
            // , description = "Creates a new habit for authenticated user"
    )
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