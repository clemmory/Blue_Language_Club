package com.example.helpers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entities.Course;
import com.example.entities.LANGUAGE;
import com.example.entities.LEVEL;
import com.example.entities.MODE;
import com.example.entities.User;
import com.example.services.CourseService;
import com.example.services.UserService;

@Configuration
public class LoadSampleData {
    @Bean
    public CommandLineRunner saveSampleData(CourseService courseService, UserService userService) {

        return datos -> {

            // Données des participants

            User user1 = User.builder()
                    .firstName("Daniela")
                    .surname("Popa")
                    .email("danipopa@blueclub.com")
                    .language(LANGUAGE.FRENCH)
                    .initial_level(LEVEL.A)
                    .build();

            User user2 = User.builder()
                    .firstName("Rosa")
                    .surname("Montero")
                    .email("danipopa@blueclub.com")
                    .language(LANGUAGE.FRENCH)
                    .initial_level(LEVEL.C)
                    .build();

            User user3 = User.builder()
                    .firstName("Clementine")
                    .surname("Mory")
                    .email("clemmory@blueclub.com")
                    .language(LANGUAGE.ENGLISH)
                    .initial_level(LEVEL.B)
                    .build();

            User user4 = User.builder()
                    .firstName("Oumayma")
                    .surname("Bombarek ")
                    .email("OmyBom@blueclub.com")
                    .language(LANGUAGE.FRENCH)
                    .initial_level(LEVEL.A)
                    .build();

            User user5 = User.builder()
                    .firstName("Celia")
                    .surname("Luque")
                    .email("celilu@blueclub.com")
                    .language(LANGUAGE.ENGLISH)
                    .initial_level(LEVEL.B)
                    .build();
            
            userService.saveUser(user1);
            userService.saveUser(user2);
            userService.saveUser(user3);
            userService.saveUser(user4);
            userService.saveUser(user5);


            // Données des cours

            Course course1 = Course.builder()
                    .title("Français - niveau A")
                    .date(LocalDate.of(2024, Month.APRIL, 22))
                    .time(LocalTime.of(12, 30))
                    .mode(MODE.PRESENTIAL)
                    .place("C.2")
                    .language(LANGUAGE.FRENCH)
                    .level(LEVEL.A)
                    .max_users(8)
                    .build();

            Course course2 = Course.builder()
                    .title("Français - niveau A")
                    .date(LocalDate.of(2024, Month.APRIL, 24))
                    .time(LocalTime.of(11, 30))
                    .mode(MODE.ONLINE)
                    .place("Teams")
                    .language(LANGUAGE.FRENCH)
                    .level(LEVEL.A)
                    .max_users(8)
                    .build();

            courseService.saveCourse(course1);
            courseService.saveCourse(course2);

            user1.setCourses(List.of(course1,course2));
            user4.setCourses(List.of(course1,course2));
            course1.setUsers(List.of(user1, user4));
            course2.setUsers(List.of(user1, user4));

            

            Course course3 = Course.builder()
                    .title("Anglais - niveau B")
                    .date(LocalDate.of(2024, Month.APRIL, 26))
                    .time(LocalTime.of(9, 00))
                    .mode(MODE.PRESENTIAL)
                    .place("C.2")
                    .language(LANGUAGE.ENGLISH)
                    .level(LEVEL.B)
                    .max_users(4)
                    .build();

            Course course4 = Course.builder()
                    .title("Anglais - niveau B")
                    .date(LocalDate.of(2024, Month.MAY, 13))
                    .time(LocalTime.of(10, 00))
                    .mode(MODE.ONLINE)
                    .place("Teams")
                    .language(LANGUAGE.ENGLISH)
                    .level(LEVEL.B)
                    .max_users(4)
                    .build();

            courseService.saveCourse(course3);
            courseService.saveCourse(course4);
        
            user3.setCourses(List.of(course3,course4));
            user5.setCourses(List.of(course3,course4));
            course3.setUsers(List.of(user3, user5));
            course4.setUsers(List.of(user3, user5));

            Course course5 = Course.builder()
                    .title("Français - niveau C")
                    .date(LocalDate.of(2024, Month.MAY, 20))
                    .time(LocalTime.of(17, 00))
                    .mode(MODE.PRESENTIAL)
                    .place("C.2")
                    .language(LANGUAGE.FRENCH)
                    .level(LEVEL.C)
                    .max_users(8)
                    .build();

            Course course6 = Course.builder()
                    .title("Français - niveau C")
                    .date(LocalDate.of(2024, Month.MAY, 20))
                    .time(LocalTime.of(15, 00))
                    .mode(MODE.ONLINE)
                    .place("Teams")
                    .language(LANGUAGE.FRENCH)
                    .level(LEVEL.C)
                    .max_users(8)
                    .build();

            courseService.saveCourse(course5);
            courseService.saveCourse(course6);
        
            user2.setCourses(List.of(course5,course6));
            course5.setUsers(List.of(user2));
            course6.setUsers(List.of(user2));

        };

    }
}