package com.parab0la.gotedh.service;

import com.parab0la.gotedh.model.User;
import com.parab0la.gotedh.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }
}
