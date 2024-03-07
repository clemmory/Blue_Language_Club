package com.blueLanguageClub.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

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

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class StudentServiceTests {

    @Mock
    private CourseDao courseDao;

    @Mock
    private StudentDao studentDao;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        student1 = Student.builder()
                .firstName("Rosa")
                .surname("Mory")
                .email("rosa.mory@gmail.com")
                .language(LANGUAGE.FRENCH)
                .initialLevel(LEVEL.B)
                .build();

        student2 = Student.builder()
                .firstName("Victor")
                .surname("Test")
                .email("victor.test@gmail.com")
                .language(LANGUAGE.ENGLISH)
                .initialLevel(LEVEL.C)
                .build();


    Course course1 = Course.builder()
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


    }

    //Test SaveStudent OK
    @Test
    @DisplayName("Test service to save a student")
    public void testSaveStudent(){

        //given
        given(studentDao.save(student1)).willReturn(student1);

        //when
        Student savedStudent = studentService.saveStudent(student1);

        //then
        assertThat(savedStudent).isNotNull();
        
    }


    //TEST findAllStudent OK
    @Test
    @DisplayName("Test to find all students")
    public void testFindAllStudents(){

        //given
        given(studentDao.findAll()).willReturn(Arrays.asList(student1, student2));

        //when
        List<Student> students = studentService.findAllStudents();

        //then 
        assertThat(students).isNotEmpty();
    }

    //Test exist by globalId - OK
    @Test
    @DisplayName("Test to find a student by global Id")
    public void testExistsStudentByGlobalId(){

        //given
        given(studentDao.existsByGlobalId(student2.getGlobalId())).willReturn(true);

        //when
        studentService.existsByGlobalId(student2.getGlobalId());

        //then
        assertThat(student2).isNotNull();
    }

}
