package com.parab0la.gotedh.controller;

import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.service.DeckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/decks")
public class DeckController {

    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Deck> getDeck(@PathVariable Long id) {
        Optional<Deck> optDeck = deckService.getDeck(id);

        return optDeck.map(deck -> new ResponseEntity<>(deck, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<Iterable<Deck>> getDecks() {
        return new ResponseEntity<>(deckService.getDecks(), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Deck> updateDeck(@PathVariable Long id, @RequestBody Deck deck) {
        Deck updatedDeck = deckService.updateDeck(id, deck);

        return updatedDeck != null ?
                new ResponseEntity<>(updatedDeck, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteDeck(@PathVariable Long id) {
        try {
            deckService.deleteDeck(id);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
