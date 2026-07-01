package com.rashtratej.habitTrackerVersion1.scheduler;

import com.rashtratej.habitTrackerVersion1.repository.HabitRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HabitScheduler {

    private final HabitRepository habitRepository;

    public HabitScheduler(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    // @Scheduled(cron = "*/10 * * * * *") for testing purpose
    public void resetHabitsDaily() {

        habitRepository.resetAllHabits();

        System.out.println(
                "Daily habit reset completed"
        );
    }
}