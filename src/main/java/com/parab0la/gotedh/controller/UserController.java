package com.parab0la.gotedh.controller;

import com.parab0la.gotedh.model.User;
import com.parab0la.gotedh.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable Integer id) {
        Optional<User> optUser = userService.getUser(id);

        return optUser.orElse(null);
    }
}
