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
import com.example.entities.User;
import com.example.services.CourseService;
import com.example.services.UserService;

@Configuration
public class LoadSampleData {
    @Bean
    public CommandLineRunner saveSampleData(CourseService courseService, UserService userService) {

        return datos -> {

        // Données du Course

            courseService.saveCourse(Course.builder()
                .title("Français - niveau A")
                .date(LocalDate.of(2024, Month.APRIL, 22))
                .time(LocalTime.of(11, 30))
                .mode(MODE.PRESENTIAL)
                .place("C.2")
                .language(LANGUAGE.FRENCH)
                .level(LEVEL.A)
                .max_users(8)
                .build());

            courseService.saveCourse(Course.builder()
                .title("Français - niveau B")
                .date(LocalDate.of(2024, Month.APRIL, 26))
                .time(LocalTime.of(17, 00))
                .mode(MODE.ONLINE)
                .place("Teams")
                .language(LANGUAGE.FRENCH)
                .level(LEVEL.B)
                .max_users(8)
                .build());

            courseService.saveCourse(Course.builder()
                .title("Français - niveau native")
                .date(LocalDate.of(2024, Month.MAY, 13))
                .time(LocalTime.of(11, 00))
                .mode(MODE.PRESENTIAL)
                .place("C.2")
                .language(LANGUAGE.FRENCH)
                .level(LEVEL.NATIVE)
                .max_users(8)
                .build());

            courseService.saveCourse(Course.builder()
                .title("Français - niveau C")
                .date(LocalDate.of(2024, Month.MAY, 20))
                .time(LocalTime.of(17, 00))
                .mode(MODE.ONLINE)
                .place("Teams")
                .language(LANGUAGE.FRENCH)
                .level(LEVEL.C)
                .max_users(8)
                .build());

            courseService.saveCourse(Course.builder()
                .title("English - Level for native")
                .date(LocalDate.of(2024, Month.MAY, 26))
                .time(LocalTime.of(11, 00))
                .mode(MODE.ONLINE)
                .place("Teams")
                .language(LANGUAGE.ENGLISH)
                .level(LEVEL.NATIVE)
                .max_users(8)
                .build());

            courseService.saveCourse(Course.builder()
                .title("English - Level B")
                .date(LocalDate.of(2024, Month.MAY, 03))
                .time(LocalTime.of(9, 00))
                .mode(MODE.PRESENTIAL)
                .place("C.1")
                .language(LANGUAGE.ENGLISH)
                .level(LEVEL.B)
                .max_users(8)
                .build());

            courseService.saveCourse(Course.builder()
                .title("English - Beginners")
                .date(LocalDate.of(2024, Month.APRIL, 22))
                .time(LocalTime.of(9, 00))
                .mode(MODE.ONLINE)
                .place("Teams")
                .language(LANGUAGE.ENGLISH)
                .level(LEVEL.NO_LEVEL)
                .max_users(8)
                .build());

            courseService.saveCourse(Course.builder()
                .title("English - Level C")
                .date(LocalDate.of(2024, Month.APRIL, 18))
                .time(LocalTime.of(17, 00))
                .mode(MODE.PRESENTIAL)
                .place("C.1")
                .language(LANGUAGE.ENGLISH)
                .level(LEVEL.C)
                .max_users(8)
                .build());




        // Données du Course

            userService.saveUser(User.builder()
                .firstName("Daniela")
                .surname("Popa")
                //.global_id(0)
                .email("danipopa@blueclub.com")
                .language(LANGUAGE.FRENCH)
                .initial_level(LEVEL.A)
                .build());

            userService.saveUser(User.builder()
                .firstName("Clementine")
                .surname("Mory")
                //.global_id(0)
                .email("clemmory@blueclub.com")
                .language(LANGUAGE.FRENCH)
                .initial_level(LEVEL.A)
                .build());

            userService.saveUser(User.builder()
                .firstName("Oumayma")
                .surname("Bombarek ")
                //.global_id(0)
                .email("OmyBom@blueclub.com")
                .language(LANGUAGE.FRENCH)
                .initial_level(LEVEL.B)
                .build());

            userService.saveUser(User.builder()
                .firstName("Celia")
                .surname("Luque")
                //.global_id(0)
                .email("celilu@blueclub.com")
                .language(LANGUAGE.FRENCH)
                .initial_level(LEVEL.B)
                .build());

            userService.saveUser(User.builder()
                .firstName("Isabel")
                .surname("Álvarez")
                //.global_id(0)
                .email("isaalva@blueclub.com")
                .language(LANGUAGE.FRENCH)
                .initial_level(LEVEL.NATIVE)
                .build());

            userService.saveUser(User.builder()
                .firstName("Rosa")
                .surname("Montero")
                //.global_id(0)
                .email("rosamonte@blueclub.com")
                .language(LANGUAGE.FRENCH)
                .initial_level(LEVEL.NATIVE)
                .build());

            userService.saveUser(User.builder()
                .firstName("Pilar")
                .surname("Colomer")
                //.global_id(0)
                .email("pilacolo@blueclub.com")
                .language(LANGUAGE.FRENCH)
                .initial_level(LEVEL.C)
                .build());

            userService.saveUser(User.builder()
                .firstName("Gabriela")
                .surname("García")
                //.global_id(0)
                .email("gabigarci@blueclub.com")
                .language(LANGUAGE.FRENCH)
                .initial_level(LEVEL.C)
                .build());

            userService.saveUser(User.builder()
                .firstName("Andrea")
                .surname("Serge")
                //.global_id(0)
                .email("andreser@blueclub.com")
                .language(LANGUAGE.ENGLISH)
                .initial_level(LEVEL.NATIVE)
                .build());

            userService.saveUser(User.builder()
                .firstName("Constanza")
                .surname("Arnau")
                //.global_id(0)
                .email("cotyarny@blueclub.com")
                .language(LANGUAGE.ENGLISH)
                .initial_level(LEVEL.NATIVE)
                .build());

            userService.saveUser(User.builder()
                .firstName("Victor")
                .surname("Machado")
                //.global_id(0)
                .email("victomacha@blueclub.com")
                .language(LANGUAGE.ENGLISH)
                .initial_level(LEVEL.B)
                .build());

            userService.saveUser(User.builder()
                .firstName("Pablo")
                .surname("Collazos")
                //.global_id(0)
                .email("pablocolla@blueclub.com")
                .language(LANGUAGE.ENGLISH)
                .initial_level(LEVEL.B)
                .build());

            userService.saveUser(User.builder()
                .firstName("Raul")
                .surname("LLibrer")
                //.global_id(0)
                .email("raullia@blueclub.com")
                .language(LANGUAGE.ENGLISH)
                .initial_level(LEVEL.NO_LEVEL)
                .build());

            userService.saveUser(User.builder()
                .firstName("Daniel")
                .surname("Román")
                //.global_id(0)
                .email("daniroma@blueclub.com")
                .language(LANGUAGE.ENGLISH)
                .initial_level(LEVEL.NO_LEVEL)
                .build());

            userService.saveUser(User.builder()
                .firstName("Carla")
                .surname("Sánchez")
                //.global_id(0)
                .email("carsan@blueclub.com")
                .language(LANGUAGE.ENGLISH)
                .initial_level(LEVEL.C)
                .build());

            userService.saveUser(User.builder()
                .firstName("Susana")
                .surname("Lagarde")
                //.global_id(0)
                .email("suslagar@blueclub.com")
                .language(LANGUAGE.ENGLISH)
                .initial_level(LEVEL.C)
                .build());

        };
    }
}