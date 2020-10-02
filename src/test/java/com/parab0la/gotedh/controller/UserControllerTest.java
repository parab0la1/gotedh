package com.parab0la.gotedh.controller;

import com.parab0la.gotedh.TestRoot;
import com.parab0la.gotedh.dto.UserDTO;
import com.parab0la.gotedh.exception.UserNotFoundException;
import com.parab0la.gotedh.model.User;
import com.parab0la.gotedh.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class UserControllerTest extends TestRoot {

    @InjectMocks
    UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        user = new User(USER_ID_BRUCE_WAYNE, BRUCE_WAYNE, 1, 1, 1, 1, new HashSet<>());
        userTwo = new User(USER_ID_HARVEY_DENT, HARVEY_DENT, 2, 2, 2, 2, new HashSet<>());
        userThree = new User(USER_ID_ALFRED_PENNYWORTH, ALFRED_PENNYWORTH, 3, 3, 3, 3, new HashSet<>());

        users = new ArrayList<>();
        users.add(user);
        users.add(userTwo);
        users.add(userThree);
    }

    @AfterEach
    void tearDown() {
        this.user = null;
        this.userTwo = null;
        this.userThree = null;
        this.users = null;
    }

    @Test
    void shouldSuccessfullyCreateAUser() {
        UserDTO userInput = this.user.toUserDTO();

        when(userService.createUser(userInput.toUser())).thenReturn(this.user);

        ResponseEntity<UserDTO> userResponse = userController.createUser(userInput);

        assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(userResponse.getBody()).isNotNull();
        assertThat(userResponse.getBody()).isEqualTo(this.user.toUserDTO());
    }

    @Test
    void shouldSuccessfullyGetAUser() {
        when(userService.getUser(user.getUserId())).thenReturn(user);

        ResponseEntity<UserDTO> userResponse = userController.getUser(user.getUserId());

        assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userResponse.getBody()).isNotNull();
        assertThat(userResponse.getBody()).isEqualTo(new UserDTO(user));
    }

    @Test
    void shouldFailGettingAUser() {
        when(userService.getUser(PHONY_USER_ID)).thenThrow(new UserNotFoundException(PHONY_USER_ID));

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUser(PHONY_USER_ID);
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(USER_NOT_FOUND_MSG);
    }

    @Test
    void shouldSuccessfullyGetUsers() {
        when(userService.getUsers()).thenReturn(users);

        ResponseEntity<List<UserDTO>> userResponse = userController.getUsers();

        assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userResponse.getBody()).isNotNull();
        assertThat((userResponse.getBody()).size()).isEqualTo(users.size());
        assertEquals(UserDTO.toUserDTOs(users), userResponse.getBody());
    }

    @Test
    void shouldSuccessfullyFetchNoUsers() {
        when(userService.getUsers()).thenReturn(new ArrayList<>());

        ResponseEntity<List<UserDTO>> userResponse = userController.getUsers();

        assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userResponse.getBody()).isNotNull();
        assertThat((userResponse.getBody()).size()).isEqualTo(0);
    }

    @Test
    void shouldSuccessfullyUpdateAUser() {
        UserDTO userInput = new UserDTO("Harald Treutiger", 5,
                5, 5, 5);

        User expectedUser = new User(this.user.getUserId(), userInput.getName(), userInput.getEloRanking(),
                userInput.getGamesPlayed(), userInput.getGamesWinPercent(), userInput.getOppsWinPercent(), new HashSet<>());

        when(userService.updateUser(this.user.getUserId(), userInput.toUser())).thenReturn(expectedUser);

        ResponseEntity<UserDTO> userResponse = userController.updateUser(user.getUserId(), userInput);

        assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userResponse.getBody()).isNotNull();
        assertThat((userResponse.getBody())).isEqualTo(expectedUser.toUserDTO());
    }

    @Test
    void shouldFailUpdatingAUser() {
        when(userService.updateUser(any(), any())).thenThrow(new UserNotFoundException(PHONY_USER_ID));

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(PHONY_USER_ID, new User(1L, "test", 1,
                    2, 3, 4, null));
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(USER_NOT_FOUND_MSG);
    }

    @Test
    void shouldSuccessfullyDeleteAUser() {
        doNothing().when(userService).deleteUser(this.user.getUserId());

        ResponseEntity<String> userResponse = userController.deleteUser(this.user.getUserId());

        assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldFailDeletingAUser() {
        doThrow(new UserNotFoundException(PHONY_USER_ID)).when(userService).deleteUser(PHONY_USER_ID);

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userController.deleteUser(PHONY_USER_ID);
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(USER_NOT_FOUND_MSG);
    }
}