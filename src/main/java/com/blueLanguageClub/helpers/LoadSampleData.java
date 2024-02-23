package com.blueLanguageClub.helpers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blueLanguageClub.entities.Course;
import com.blueLanguageClub.entities.LANGUAGE;
import com.blueLanguageClub.entities.LEVEL;
import com.blueLanguageClub.entities.MODE;
import com.blueLanguageClub.entities.Student;
import com.blueLanguageClub.services.CourseService;
import com.blueLanguageClub.services.StudentService;

@Configuration
public class LoadSampleData {
    @Bean
    public CommandLineRunner saveSampleData(CourseService courseService, StudentService studentService) {

        return datos -> {

        // Données du Users

            Student student1 = Student.builder()
                .firstName("Daniela")
                .surname("Popa")
                .email("danipopa@blueclub.com")
                .language(LANGUAGE.FRENCH)
                .initialLevel(LEVEL.A)
                .build();

            Student student2 = Student.builder()
                .firstName("Clem")
                .surname("Mory")
                .email("clemory@blueclub.com")
                .language(LANGUAGE.FRENCH)
                .initialLevel(LEVEL.B)
                .build();
                
            Student student3 = Student.builder()
                .firstName("Rosa")
                .surname("Montero")
                .email("rosamonte@blueclub.com")
                .language(LANGUAGE.ENGLISH)
                .initialLevel(LEVEL.B)
                .build();
                
            Student student4 = Student.builder()
                .firstName("Victor")
                .surname("Machado")
                .email("victomacha@blueclub.com")
                .language(LANGUAGE.ENGLISH)
                .initialLevel(LEVEL.NO_LEVEL)
                .build();


        // Données du Course

            courseService.saveCourse(Course.builder()
                .title("Français - niveau A")
                .date(LocalDate.of(2024, Month.APRIL, 02))
                .time(LocalTime.of(11, 30))
                .mode(MODE.ONSITE)
                .place("C.2")
                .language(LANGUAGE.FRENCH)
                .level(LEVEL.A)
                .max_students(8)
                .students(List.of(student1, student2))
                .build());

            courseService.saveCourse(Course.builder()
                .title("English - Level B")
                .date(LocalDate.of(2024, Month.MARCH, 22))
                .time(LocalTime.of(10, 30))
                .mode(MODE.ONLINE)
                .place("Teams")
                .language(LANGUAGE.FRENCH)
                .level(LEVEL.A)
                .max_students(8)
                .students(List.of(student3, student4))
                .build());


            student1.setCourses(List.of(courseService.findByIdCourse(1)));
            student2.setCourses(List.of(courseService.findByIdCourse(1)));
            student3.setCourses(List.of(courseService.findByIdCourse(2)));
            student4.setCourses(List.of(courseService.findByIdCourse(2)));

        

            

        };
    }
}