package com.rashtratej.habitTrackerVersion1.controller;

import com.rashtratej.habitTrackerVersion1.dto.CreateHabitRequest;
import com.rashtratej.habitTrackerVersion1.dto.HabitResponse;
import com.rashtratej.habitTrackerVersion1.dto.HabitStatsResponse;
import com.rashtratej.habitTrackerVersion1.dto.UpdateHabitRequest;
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

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Habit> completeHabit(@PathVariable Long id) {

        Habit updatedHabit =
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
    public ResponseEntity<Habit> updateHabit(@PathVariable Long id, @Valid @RequestBody UpdateHabitRequest request) {

        Habit updatedHabit =
                habitService.updateHabit(
                        id,
                        request
                );

        return ResponseEntity.ok(
                updatedHabit
        );
    }



//    @PutMapping("/{id}")
//    public ResponseEntity<HabitResponse>
//    updateHabit(@PathVariable Long id,
//                @Valid @RequestBody
//            UpdateHabitRequest request
//    ) {
//
//        HabitResponse habit =
//                habitService.updateHabit(
//                        id,
//                        request
//                );
//
//        return ResponseEntity.ok(
//                habit
//        );
//    }
}