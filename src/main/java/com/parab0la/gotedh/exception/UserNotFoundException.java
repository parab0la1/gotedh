package com.parab0la.gotedh.exception;

import javax.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(Long id) {
        super("The user with id: " + id + " could not be found");
    }
}
