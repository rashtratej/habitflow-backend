package com.rashtratej.habitTrackerVersion1.repository;

import com.rashtratej.habitTrackerVersion1.entity.Habit;
import com.rashtratej.habitTrackerVersion1.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class HabitRepositoryTest {

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private UserRepository userRepository;


    private User createTestUser() {
        User user = new User();

        user.setUserName("Rashtra" + System.nanoTime());

        user.setEmail(
                "test" + System.nanoTime() + "@test.com"
        );

        user.setPassword("password");
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Test
    void shouldSaveHabit() {

        User user = createTestUser();

        Habit habit = new Habit();
        habit.setTitle("Morning Run");
        habit.setDescription("Run 5km");
        habit.setCompleted(false);
        habit.setCreatedAt(LocalDateTime.now());
        habit.setUser(user);

        Habit savedHabit = habitRepository.save(habit);

        assertNotNull(savedHabit.getId());
        assertEquals("Morning Run", savedHabit.getTitle());
    }

    @Test
    void shouldFindHabitsByUser() {

        User user = createTestUser();

        Habit habit = new Habit();
        habit.setTitle("Gym");
        habit.setCompleted(false);
        habit.setCreatedAt(LocalDateTime.now());
        habit.setUser(user);

        habitRepository.save(habit);

        Page<Habit> habits =
                habitRepository.findByUser(
                        user,
                        PageRequest.of(0,5)
                );

        assertEquals(1, habits.getContent().size());
    }

    @Test
    void shouldFindByIdAndUser() {

        User user = createTestUser();

        Habit habit = new Habit();
        habit.setTitle("Reading");
        habit.setCompleted(false);
        habit.setCreatedAt(LocalDateTime.now());
        habit.setUser(user);

        Habit saved = habitRepository.save(habit);

        var found =
                habitRepository.findByIdAndUser(
                        saved.getId(),
                        user
                );

        assertTrue(found.isPresent());
    }

    @Test
    void shouldCountHabitsByUser() {

        User user = createTestUser();

        Habit h1 = new Habit();
        h1.setTitle("Run");
        h1.setCompleted(false);
        h1.setCreatedAt(LocalDateTime.now());
        h1.setUser(user);

        Habit h2 = new Habit();
        h2.setTitle("Read");
        h2.setCompleted(false);
        h2.setCreatedAt(LocalDateTime.now());
        h2.setUser(user);

        habitRepository.save(h1);
        habitRepository.save(h2);

        long count =
                habitRepository.countByUser(user);

        assertEquals(2, count);
    }

    @Test
    void shouldCountCompletedHabits() {

        User user = createTestUser();

        Habit habit = new Habit();
        habit.setTitle("Workout");
        habit.setCompleted(true);
        habit.setCreatedAt(LocalDateTime.now());
        habit.setUser(user);

        habitRepository.save(habit);

        long completed =
                habitRepository
                        .countByUserAndCompleted(
                                user,
                                true
                        );

        assertEquals(1, completed);
    }
}