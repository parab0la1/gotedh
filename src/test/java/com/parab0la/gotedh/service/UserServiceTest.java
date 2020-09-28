package com.parab0la.gotedh.service;

import com.parab0la.gotedh.TestRoot;
import com.parab0la.gotedh.exception.UserNotFoundException;
import com.parab0la.gotedh.model.User;
import com.parab0la.gotedh.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTest extends TestRoot {

    @InjectMocks
    UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        this.user = new User(USER_ID_BRUCE_WAYNE, BRUCE_WAYNE, 1, 1, 1, 1, new HashSet<>());
        this.userTwo = new User(USER_ID_HARVEY_DENT, HARVEY_DENT, 2, 2, 2, 2, new HashSet<>());
        this.userThree = new User(USER_ID_ALFRED_PENNYWORTH, ALFRED_PENNYWORTH, 3, 3, 3, 3, new HashSet<>());

        this.users = new ArrayList<>();
        this.users.add(user);
        this.users.add(userTwo);
        this.users.add(userThree);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldSuccessfullyGetAUser() {
        when(userRepository.findById(this.user.getUserId())).thenReturn(Optional.of(this.user));

        assertThat(userService.getUser(this.user.getUserId())).isEqualTo(this.user);
    }

    @Test
    void shouldFailGettingAUser() {
        when(userRepository.findById(PHONY_USER_ID)).thenThrow(new UserNotFoundException(PHONY_USER_ID));

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUser(PHONY_USER_ID);
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(USER_NOT_FOUND_MSG);
    }

    @Test
    void shouldSuccessfullyCreateAUser() {
        when(userRepository.save(this.user)).thenReturn(this.user);

        assertThat(userService.createUser(this.user)).isEqualTo(this.user);
    }

    @Test
    void shouldSuccessfullyGetAllUsers() {
        when(userRepository.findAll()).thenReturn(this.users);

        assertThat(userService.getUsers()).isEqualTo(this.users);
    }

    @Test
    void shouldSuccessfullyGetAllUsersEmptyResult() {
        List<User> emptyUsers = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(emptyUsers);

        assertThat(userService.getUsers()).isEqualTo(emptyUsers);
    }

    @Test
    void shouldSuccessfullyUpdateAUser() {
        User newUser = new User(1214124L, this.user.getName(), 55,
                55, 55, 55, new HashSet<>());

        User expectedUser = new User(this.user.getUserId(), this.user.getName(), 55,
                55, 55, 55, new HashSet<>());

        when(userRepository.findById(this.user.getUserId())).thenReturn(Optional.of(this.user));
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        User actualUser = userService.updateUser(this.user.getUserId(), newUser);

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void shouldFailUpdatingAUser() {
        when(userRepository.findById(PHONY_USER_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(PHONY_USER_ID, new User());
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(USER_NOT_FOUND_MSG);
    }

    @Test
    void shouldSuccessfullyDeleteAUser() {
        when(userRepository.existsById(this.user.getUserId())).thenReturn(true);

        userService.deleteUser(this.user.getUserId());
    }

    @Test
    void shouldSuccessfullyDeleteANonExistingUser() {
        when(userRepository.existsById(PHONY_USER_ID)).thenReturn(false);

        userService.deleteUser(PHONY_USER_ID);
    }
}