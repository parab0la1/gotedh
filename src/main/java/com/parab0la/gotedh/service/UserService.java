package com.parab0la.gotedh.service;

import com.parab0la.gotedh.exception.UserNotFoundException;
import com.parab0la.gotedh.model.User;
import com.parab0la.gotedh.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId) {
        logger.debug("Getting user with id: {}", userId);

        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public User createUser(User user) {
        logger.debug("Creating user: {}", user);

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        logger.debug("Getting all users");

        return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public User updateUser(Long userId, User newUser) {
        Optional<User> userToUpdate = userRepository.findById(userId);

        if (!userToUpdate.isPresent())
            throw new UserNotFoundException(userId);

        logger.debug("User exists, proceeding with update");

        return userRepository.save(updateUser(userToUpdate.get(), newUser));
    }

    public void deleteUser(Long userId) {
        logger.debug("Deleting user with id: {} ", userId);

        if (!userRepository.existsById(userId))
            logger.error("Could not find a user with id: {}", userId);

        userRepository.deleteById(userId);
    }

    private User updateUser(User userToUpdate, User newUser) {
        logger.debug("Updating user with id: {} with new values from: {}", userToUpdate.getUserId(), newUser);

        userToUpdate.setName(newUser.getName());
        userToUpdate.setEloRanking(newUser.getEloRanking());
        userToUpdate.setGamesPlayed(newUser.getGamesPlayed());
        userToUpdate.setGamesWinPercent(newUser.getGamesWinPercent());
        userToUpdate.setOppsWinPercent(newUser.getOppsWinPercent());

        return userToUpdate;
    }
}
