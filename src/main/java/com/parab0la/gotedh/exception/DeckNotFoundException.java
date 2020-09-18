package com.parab0la.gotedh.exception;

import javax.persistence.EntityNotFoundException;

public class DeckNotFoundException extends EntityNotFoundException {

    public DeckNotFoundException() {
    }

    public DeckNotFoundException(Long id) {
        super("The deck with id: " + id + " could not be found");
    }
}
