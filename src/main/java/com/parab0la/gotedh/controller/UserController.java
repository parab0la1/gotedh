package com.parab0la.gotedh.controller;

import com.parab0la.gotedh.dto.UserDTO;
import com.parab0la.gotedh.model.User;
import com.parab0la.gotedh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userInput) {
        User user = userInput.toUser();

        User userResponse = userService.createUser(user);

        return new ResponseEntity<>(userResponse.toUserDTO(), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUser(userId).toUserDTO(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(UserDTO.toUserDTOs(userService.getUsers()), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userInput) {
        User user = userInput.toUser();

        User updatedUser = userService.updateUser(id, user);

        return new ResponseEntity<>(updatedUser.toUserDTO(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
