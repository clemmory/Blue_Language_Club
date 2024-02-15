package com.example.helpers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entities.Course;
import com.example.entities.LANGUAGE;
import com.example.entities.LEVEL;
import com.example.entities.MODE;
import com.example.services.CourseService;
import com.example.services.UserService;

@Configuration
public class LoadSampleData {
    @Bean
    public CommandLineRunner saveSampleData(CourseService courseService, UserService userService) {

        return datos -> {

            courseService.save(Course.builder()
                .title("English - Beginners")
                .date(LocalDate.of(2024, Month.APRIL, 22))
                .time(LocalTime.of(9, 00))
                .mode(MODE.ONLINE)
                .place("Teams")
                .language(LANGUAGE.ENGLISH)
                .level(LEVEL.NO_LEVEL)
                .max_users(8)
                .build());

            courseService.save(Course.builder()
                .title("Français - niveau A")
                .date(LocalDate.of(2024, Month.APRIL, 22))
                .time(LocalTime.of(11, 30))
                .mode(MODE.PRESENTIAL)
                .place("C.1")
                .language(LANGUAGE.FRENCH)
                .level(LEVEL.A)
                .max_users(8)
                .build());

            courseService.save(Course.builder()
                .title("Français - niveau B")
                .date(LocalDate.of(2024, Month.APRIL, 26))
                .time(LocalTime.of(17, 00))
                .mode(MODE.ONLINE)
                .place("Teams")
                .language(LANGUAGE.FRENCH)
                .level(LEVEL.B)
                .max_users(8)
                .build());

            courseService.save(Course.builder()
                .title("English - Level B")
                .date(LocalDate.of(2024, Month.MAY, 03))
                .time(LocalTime.of(9, 00))
                .mode(MODE.ONLINE)
                .place("Teams")
                .language(LANGUAGE.ENGLISH)
                .level(LEVEL.B)
                .max_users(8)
                .build());

            courseService.save(Course.builder()
                .title("Français - niveau native")
                .date(LocalDate.of(2024, Month.MAY, 13))
                .time(LocalTime.of(11, 00))
                .mode(MODE.PRESENTIAL)
                .place("C.2")
                .language(LANGUAGE.FRENCH)
                .level(LEVEL.NATIVE)
                .max_users(8)
                .build());

            courseService.save(Course.builder()
                .title("Français - niveau C")
                .date(LocalDate.of(2024, Month.MAY, 20))
                .time(LocalTime.of(17, 00))
                .mode(MODE.ONLINE)
                .place("Teams")
                .language(LANGUAGE.FRENCH)
                .level(LEVEL.C)
                .max_users(8)
                .build());

            courseService.save(Course.builder()
                .title("English - Level for native")
                .date(LocalDate.of(2024, Month.MAY, 26))
                .time(LocalTime.of(11, 00))
                .mode(MODE.ONLINE)
                .place("Teams")
                .language(LANGUAGE.ENGLISH)
                .level(LEVEL.NATIVE)
                .max_users(8)
                .build());

        };
    }
}