package com.parab0la.gotedh.controller;

import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.service.DeckService;
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

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/decks")
public class DeckController {

    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @PostMapping
    public Deck createDeck(@RequestBody Deck deck) {
        return deckService.createDeck(deck);
    }

    @GetMapping(path = "/{deckId}")
    public Deck getDeck(@PathVariable Integer deckId) {
        Optional<Deck> optDeck = deckService.getDeck(deckId);

        return optDeck.orElse(null);
    }

    @GetMapping
    public Iterable<Deck> getDecks() {
        return deckService.getDecks();
    }

    @PutMapping(path = "/{deckId}")
    public ResponseEntity<Deck> updateDeck(@PathVariable Integer deckId, @RequestBody Deck deck) {
        Deck updatedDeck = deckService.updateDeck(deckId, deck);

        return updatedDeck != null ?
                new ResponseEntity<>(updatedDeck, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{deckId}")
    public ResponseEntity<Deck> deleteDeck(@PathVariable Integer deckId) {
        try {
            deckService.deleteDeck(deckId);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
