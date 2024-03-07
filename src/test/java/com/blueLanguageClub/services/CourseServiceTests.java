package com.blueLanguageClub.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.blueLanguageClub.dao.CourseDao;
import com.blueLanguageClub.dao.StudentDao;
import com.blueLanguageClub.entities.Course;
import com.blueLanguageClub.entities.LANGUAGE;
import com.blueLanguageClub.entities.LEVEL;
import com.blueLanguageClub.entities.MODE;
import com.blueLanguageClub.entities.Student;

import static org.assertj.core.api.Assertions.assertThat;

// Para seguir el enfoque de BDD con Mockito
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CourseServiceTests {

    @Mock
    private CourseDao courseDao;

    @Mock
    private StudentDao studentDao;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        Student student = Student.builder()
                .firstName("Rosa")
                .surname("Mory")
                .email("rosa.mory@gmail.com")
                .language(LANGUAGE.FRENCH)
                .initialLevel(LEVEL.B)
                .build();

        course1 = Course.builder()
                .id(2)
                .title("Classe Anglais niveau B")
                .date(LocalDate.of(2024, 05, 14))
                .time(LocalTime.of(11,45))
                .mode(MODE.ONLINE)
                .place("TEAMS")
                .language(LANGUAGE.FRENCH)
                .level(LEVEL.B)
                .max_students(8)
                .build();

        course2 = Course.builder()
                .id(3)
                .title("Classe Français niveau A")
                .date(LocalDate.of(2024, 06, 20))
                .time(LocalTime.of(18,45))
                .mode(MODE.ONLINE)
                .place("TEAMS")
                .language(LANGUAGE.ENGLISH)
                .level(LEVEL.A)
                .max_students(8)
                .build();
    }

    //TEST saveCourse OK
    @Test
    @DisplayName("Test de service to save a course")
    public void testSaveCourse() {

        // given
        given(courseDao.save(course1)).willReturn(course1);

        // when
        Course savedCourse = courseService.saveCourse(course1);

        // then
        assertThat(savedCourse).isNotNull();
    }

    //TEST findAllCourses OK
    @Test
    @DisplayName("Test to find all courses")
    public void testFindAllCourses(){

        //given
        given(courseDao.findAll()).willReturn(Arrays.asList(course1, course2));

        //when
        List<Course> courses = courseService.findAllCourses();

        //then 
        assertThat(courses).isNotEmpty();
    }

    //TEST findByIdCourse OK
    @Test
    @DisplayName("Test to find a course by id")
    public void testFindCourseById(){

        //given
        given(courseDao.findById(course1.getId())).willReturn(Optional.of(course1));

        //when
        Course course3 = courseService.findByIdCourse(course1.getId());

        //then
        assertThat(course3).isNotNull();



    }


}
