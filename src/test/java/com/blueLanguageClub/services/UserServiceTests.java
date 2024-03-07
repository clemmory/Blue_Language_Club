package com.blueLanguageClub.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.blueLanguageClub.customsExceptions.ResourceNotFoundException;
import com.blueLanguageClub.user.Role;
import com.blueLanguageClub.user.User;
import com.blueLanguageClub.user.UserRepository;
import com.blueLanguageClub.user.UserServiceImpl;

// Mockito simula las depedencias, todas las dependencias van a ser simuladas.
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1)
                .firstName("Vict")
                .lastName("Rafael")
                .email("vrmachado@gmail.com")
                .password("Temp2023$$")
                .role(Role.ADMIN)
                .build();
    }

    //TEST OK
    @Test
    @DisplayName("Test to save a user and generate an exception")
    public void testSaveUserWithThrowException() {

        // given
        given(userRepository.findByEmail(user.getEmail()))
                .willReturn(Optional.of(user));

        // when
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.add(user);
        });

        // Then
        verify(userRepository, never()).save(any(User.class));

    }

}
