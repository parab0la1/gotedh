package com.parab0la.gotedh.controller;

import com.parab0la.gotedh.dto.DeckDTO;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class DeckController {

    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @PostMapping(path = "/users/{userId}/decks")
    public ResponseEntity<DeckDTO> createDeck(@PathVariable Long userId, @RequestBody Deck deck) {
        return new ResponseEntity<>(new DeckDTO(deckService.createDeck(userId, deck)), HttpStatus.CREATED);
    }

    @GetMapping(path = "/decks/{id}")
    public ResponseEntity<DeckDTO> getDeck(@PathVariable Long id) {
        Optional<Deck> optDeck = deckService.getDeck(id);

        return optDeck.map(deck -> new ResponseEntity<>(new DeckDTO(deck), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/users/{userId}/decks/{deckId}")
    public ResponseEntity<DeckDTO> getUserDeck(@PathVariable Long userId, @PathVariable Long deckId) {
        return new ResponseEntity<>(new DeckDTO(deckService.getUserDeck(userId, deckId)), HttpStatus.OK);
    }

    @GetMapping(path = "/decks")
    public ResponseEntity<List<DeckDTO>> getDecks() {
        return new ResponseEntity<>(DeckDTO.toDeckDTOs(deckService.getDecks()), HttpStatus.OK);
    }

    @GetMapping(path = "/users/{userId}/decks")
    public ResponseEntity<List<DeckDTO>> getUserDecks(@PathVariable Long userId) {
        return new ResponseEntity<>(DeckDTO.toDeckDTOs(deckService.getUserDecks(userId)), HttpStatus.OK);
    }

    @PutMapping(path = "/users/{userId}/decks/{deckId}")
    public ResponseEntity<DeckDTO> updateDeck(@PathVariable Long userId, @PathVariable Long deckId, @RequestBody Deck deck) {
        Deck updatedDeck = deckService.updateDeck(userId, deckId, deck);

        return deckService.updateDeck(userId, deckId, deck) != null ?
                new ResponseEntity<>(new DeckDTO(updatedDeck), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/users/{userId}/decks/{deckId}")
    public ResponseEntity<String> deleteDeck(@PathVariable Long userId, @PathVariable Long deckId) {
        deckService.deleteDeck(userId, deckId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
