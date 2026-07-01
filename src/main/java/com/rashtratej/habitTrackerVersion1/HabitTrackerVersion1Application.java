package com.rashtratej.habitTrackerVersion1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HabitTrackerVersion1Application {

	public static void main(String[] args) {

		SpringApplication.run(HabitTrackerVersion1Application.class, args);
	}

}
