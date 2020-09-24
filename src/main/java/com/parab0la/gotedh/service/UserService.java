package com.parab0la.gotedh.service;

import com.parab0la.gotedh.exception.UserNotFoundException;
import com.parab0la.gotedh.model.User;
import com.parab0la.gotedh.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public User updateUser(Long userId, User newUser) {
        Optional<User> userToUpdate = userRepository.findById(userId);

        if (!userToUpdate.isPresent())
            throw new UserNotFoundException(userId);

        return userRepository.save(updateUser(userToUpdate.get(), newUser));
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId))
            throw new UserNotFoundException(userId);

        userRepository.deleteById(userId);
    }

    private User updateUser(User userToUpdate, User newUser) {
        userToUpdate.setName(newUser.getName());
        userToUpdate.setEloRanking(newUser.getEloRanking());
        userToUpdate.setGamesPlayed(newUser.getGamesPlayed());
        userToUpdate.setGamesWinPercent(newUser.getGamesWinPercent());
        userToUpdate.setOppsWinPercent(newUser.getOppsWinPercent());

        return userToUpdate;
    }
}
