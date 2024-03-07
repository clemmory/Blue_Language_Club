package com.blueLanguageClub.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.blueLanguageClub.user.Role;
import com.blueLanguageClub.user.User;
import com.blueLanguageClub.user.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User user0;

    @BeforeEach
    void setUp() {
        user0 = User.builder()
                .firstName("Test User 0")
                .lastName("Esquer")
                .password("123456")
                .email("user0@gmail.com")
                .role(Role.USER)
                .build();
    }

    // TEST to add a user OK
    @Test
    @DisplayName("Add user")
    public void testAddUser() {

        // given

        User user = User.builder()
                .firstName("Test User 1")
                .lastName("Machado")
                .password("123456")
                .email("v@gmail.com")
                .role(Role.USER)
                .build();

        // when

        User userAdded = userRepository.save(user);

        // then

        assertThat(userAdded).isNotNull();
        assertThat(userAdded.getId()).isGreaterThan(0L);

    }

    // Test to display all users OK
    @DisplayName("Test to list all users")
    @Test
    public void testFindAllUsers() {

        // given
        User user1 = User.builder()
                .firstName("Test User 1")
                .lastName("Machado")
                .password("123456")
                .email("v@gmail.com")
                .role(Role.USER)
                .build();

        userRepository.save(user0);
        userRepository.save(user1);

        // when
        List<User> usuarios = userRepository.findAll();

        // then
        assertThat(usuarios).isNotNull();
        assertThat(usuarios.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test to get an user with its id")
    public void findUserById() {

        // given

        userRepository.save(user0);

        // when

        User user = userRepository.findById(user0.getId()).get();

        // then

        assertThat(user.getId()).isNotEqualTo(0L);

    }

    @Test
    @DisplayName("Test to update a user")
    public void testUpdateUser() {

        // given

        userRepository.save(user0);

        // when

        User savedUser = userRepository.findByEmail(user0.getEmail()).get();

        savedUser.setLastName("Perico");
        savedUser.setFirstName("Juan");
        savedUser.setEmail("jp@gg.com");

        User userUpdated = userRepository.save(savedUser);

        // then

        assertThat(userUpdated.getEmail()).isEqualTo("jp@gg.com");
        assertThat(userUpdated.getFirstName()).isEqualTo("Juan");

    }

    @DisplayName("Test to delete a user")
    @Test
    public void testDeleteUser() {

        // given
        userRepository.save(user0);

        // when
        userRepository.delete(user0);
        Optional<User> optionalUser = userRepository.findByEmail(user0.getEmail());

        // then
        assertThat(optionalUser).isEmpty();
    }
}
