package com.blueLanguageClub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // The New Lambda DSL Syntax

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/users/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/api/courses/{courseId}/students/{globalId}**").hasAnyAuthority("ADMIN","USER");
                    auth.requestMatchers(HttpMethod.GET, "/api/students/{globalId}/mycourses**").hasAnyAuthority("ADMIN","USER");
                    auth.requestMatchers(HttpMethod.GET, "/api/students/{globalId}/courses**").hasAnyAuthority("ADMIN","USER");
                    auth.requestMatchers(HttpMethod.GET, "/api/courses**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/api/students**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/api/courses/available**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/api/students/{globalId}**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/api/courses/{courseId}/students**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/api/courses**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/api/students**").hasAuthority("ADMIN"); 
                    auth.requestMatchers(HttpMethod.PUT, "/api/courses/{idCourse}/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/api/students/{globalId}/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/courses/{idCourse}**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/students/{globalId}**").hasAuthority("ADMIN");
                    auth.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults()).build();


               
        // return http.csrf().disable()
        //     .authorizeHttpRequests()
        //     .requestMatchers(HttpMethod.GET, "/productos/**").permitAll()
        //     .and().authorizeHttpRequests()
        //     .requestMatchers("/users/**").permitAll()
        //     .and().authorizeHttpRequests()
        //     .requestMatchers(HttpMethod.POST, "/productos/**").hasAuthority("ADMIN")
        //     .and().authorizeHttpRequests()
        //     .requestMatchers(HttpMethod.PUT, "/productos/**").hasAuthority("ADMIN")
        //     .and().authorizeHttpRequests()
        //     .requestMatchers(HttpMethod.DELETE, "/productos/**").hasAuthority("ADMIN")
        //    .anyRequest().authenticated().and().httpBasic(withDefaults()).build();
     } 

}
