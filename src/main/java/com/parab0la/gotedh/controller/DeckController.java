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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class DeckController {

    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @PostMapping(path = "/users/{userId}/decks")
    public ResponseEntity<DeckDTO> createDeck(@PathVariable Long userId, @RequestBody DeckDTO deckInput) {
        Deck deck = deckInput.toDeck();

        Deck deckDTO = deckService.createDeck(userId, deck);

        return new ResponseEntity<>(deckDTO.toDeckDTO(), HttpStatus.CREATED);
    }

    @GetMapping(path = "/decks/{id}")
    public ResponseEntity<DeckDTO> getDeck(@PathVariable Long id) {
        return new ResponseEntity<>(deckService.getDeck(id).toDeckDTO(), HttpStatus.OK);
    }

    @GetMapping(path = "/users/{userId}/decks/{deckId}")
    public ResponseEntity<DeckDTO> getUserDeck(@PathVariable Long userId, @PathVariable Long deckId) {
        return new ResponseEntity<>(deckService.getUserDeck(userId, deckId).toDeckDTO(), HttpStatus.OK);
    }

    @GetMapping(path = "/decks")
    public ResponseEntity<List<DeckDTO>> getDecks() {
        return new ResponseEntity<>(Deck.toDeckDTOs(deckService.getDecks()), HttpStatus.OK);
    }

    @GetMapping(path = "/users/{userId}/decks")
    public ResponseEntity<List<DeckDTO>> getUserDecks(@PathVariable Long userId) {
        return new ResponseEntity<>(Deck.toDeckDTOs(deckService.getDecks(userId)), HttpStatus.OK);
    }

    @PutMapping(path = "/users/{userId}/decks/{deckId}")
    public ResponseEntity<DeckDTO> updateDeck(@PathVariable Long userId, @PathVariable Long deckId, @RequestBody DeckDTO deckInput) {
        Deck deck = deckInput.toDeck();

        Deck updatedDeck = deckService.updateDeck(userId, deckId, deck);

        return new ResponseEntity<>(updatedDeck.toDeckDTO(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/users/{userId}/decks/{deckId}")
    public ResponseEntity<String> deleteDeck(@PathVariable Long userId, @PathVariable Long deckId) {
        deckService.deleteDeck(userId, deckId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
