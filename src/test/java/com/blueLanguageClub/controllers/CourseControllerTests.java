package com.blueLanguageClub.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.blueLanguageClub.entities.Course;
import com.blueLanguageClub.entities.LANGUAGE;
import com.blueLanguageClub.entities.LEVEL;
import com.blueLanguageClub.entities.MODE;
import com.blueLanguageClub.entities.Student;
import com.blueLanguageClub.services.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc 
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CourseControllerTests {

        @Autowired
        private MockMvc mockMvc; 

        @MockBean
        private CourseService courseService;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private WebApplicationContext context;

        @BeforeEach
        public void setUp() {
                mockMvc = MockMvcBuilders
                                .webAppContextSetup(context)
                                .apply(springSecurity())
                                .build();
        }

        // Test other user than admin cannot create a course OK
        @Test
        @DisplayName("Test to keep a course without an authorisation as ADMIN")
        void testSaveCourse() throws Exception {

                Course course = Course.builder()
                                .title("Français niveau C")
                                .date(LocalDate.of(2024,Month.APRIL,13))
                                .time((LocalTime.of(13,45)))
                                .mode((MODE.ONLINE))
                                .place("Teams")
                                .language(LANGUAGE.FRENCH)
                                .level(LEVEL.C)
                                .max_students(8)
                                .build();

                String jsonStringCourse = objectMapper.writeValueAsString(course);

                mockMvc.perform(post("/api/courses")
                        .contentType("application/json")
                        .content(jsonStringCourse))
                        .andDo(print())
                        .andExpect(status().isUnauthorized());

        }

        // @Test
        // @DisplayName("Test to keep a course as ADMIN")
        // @WithMockUser(username = "admin01@gmail.com", authorities = { "ADMIN" }) // puede ser {"ADMIN", "USER"}
        // void testSaveCourseAuthorized() throws Exception {

        //         Course course = Course.builder()
        //                         .title("Français niveau C")
        //                         .date(LocalDate.of(2024,Month.APRIL,13))
        //                         .time((LocalTime.of(13,45)))
        //                         .mode(MODE.ONLINE)
        //                         .place("Teams")
        //                         .language(LANGUAGE.FRENCH)
        //                         .level(LEVEL.C)
        //                         .max_students(8)
        //                         .build();

                                

        //         String jsonStringCourse = objectMapper.writeValueAsString(course);

        //         mockMvc.perform(post("/api/courses")
        //                 .contentType("application/json")
        //                 .content(jsonStringCourse))
        //                 .andDo(print())
        //                 .andExpect(status().isCreated());


        // }
}
