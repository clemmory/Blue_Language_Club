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

        // Données du Users

            User user1 = User.builder()
                .firstName("Daniela")
                .surname("Popa")
                .email("danipopa@blueclub.com")
                .language(LANGUAGE.FRENCH)
                .initial_level(LEVEL.A)
                .build();

            User user2 = User.builder()
                .firstName("Clem")
                .surname("Mory")
                .email("clemory@blueclub.com")
                .language(LANGUAGE.FRENCH)
                .initial_level(LEVEL.B)
                .build();
                
            User user3 = User.builder()
                .firstName("Rosa")
                .surname("Montero")
                .email("rosamonte@blueclub.com")
                .language(LANGUAGE.ENGLISH)
                .initial_level(LEVEL.B)
                .build();
                
            User user4 = User.builder()
                .firstName("Victor")
                .surname("Machado")
                .email("victomacha@blueclub.com")
                .language(LANGUAGE.ENGLISH)
                .initial_level(LEVEL.NO_LEVEL)
                .build();


        // Données du Course

            courseService.saveCourse(Course.builder()
                .title("Français - niveau A")
                .date(LocalDate.of(2024, Month.APRIL, 02))
                .time(LocalTime.of(11, 30))
                .mode(MODE.PRESENTIAL)
                .place("C.2")
                .language(LANGUAGE.FRENCH)
                .level(LEVEL.A)
                .max_users(8)
                .users(List.of(user1, user2))
                .build());

            courseService.saveCourse(Course.builder()
                .title("English - Level B")
                .date(LocalDate.of(2024, Month.MARCH, 22))
                .time(LocalTime.of(10, 30))
                .mode(MODE.ONLINE)
                .place("Teams")
                .language(LANGUAGE.FRENCH)
                .level(LEVEL.A)
                .max_users(8)
                .users(List.of(user3, user4))
                .build());


            user1.setCourses(List.of(courseService.findByIdCourse(1)));
            user2.setCourses(List.of(courseService.findByIdCourse(1)));
            user3.setCourses(List.of(courseService.findByIdCourse(2)));
            user4.setCourses(List.of(courseService.findByIdCourse(2)));

        

            

        };
    }
}