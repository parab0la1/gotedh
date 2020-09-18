package com.parab0la.gotedh.controller;

import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.User;
import com.parab0la.gotedh.service.DeckService;
import com.parab0la.gotedh.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class DeckControllerTest {

    @InjectMocks
    DeckController deckController;

    @Mock
    private DeckService deckService;

    @Mock
    private UserService userService;

    private List<Deck> decks;
    private Deck deck;
    private Deck deckTwo;
    private Deck deckThree;
    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User(1L, "Joel Nilsson", 1,
                5, 56, 46, new HashSet<>());

        this.deck = new Deck(1L, "Kalamax", 1,
                2, 2, 2, 2, new User());
        this.deckTwo = new Deck(2L, "Anje", 1,
                2, 2, 2, 2, new User());
        this.deckThree = new Deck(3L, "Kenrith", 1,
                2, 2, 2, 2, new User());

        this.decks = new ArrayList<>();
        this.decks.add(deck);
        this.decks.add(deckTwo);
        this.decks.add(deckThree);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldSuccessfullyFetchADeck() {
        when(deckService.getDeck(1L)).thenReturn(Optional.of(this.deck));
        when(deckService.getDeck(2L)).thenReturn(Optional.of(this.deckTwo));
        when(deckService.getDeck(3L)).thenReturn(Optional.of(this.deckThree));

        ResponseEntity<Deck> deckResponseOne = deckController.getDeck(1L);
        ResponseEntity<Deck> deckResponseTwo = deckController.getDeck(2L);
        ResponseEntity<Deck> deckResponseThree = deckController.getDeck(3L);

        assertThat(deckResponseOne.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deckResponseTwo.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deckResponseThree.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(deckResponseOne.getBody()).isNotNull();
        assertThat(deckResponseTwo.getBody()).isNotNull();
        assertThat(deckResponseThree.getBody()).isNotNull();

        assertThat(deckResponseOne.getBody().getCommander()).isEqualTo("Kalamax");
        assertThat(deckResponseTwo.getBody().getCommander()).isEqualTo("Anje");
        assertThat(deckResponseThree.getBody().getCommander()).isEqualTo("Kenrith");
    }

    @Test
    void shouldFailFindingFetchADeck() {
        Optional<Deck> noDeck = Optional.empty();
        when(deckService.getDeck(4L)).thenReturn(noDeck);

        ResponseEntity<Deck> deckResponse = deckController.getDeck(4L);

        assertThat(deckResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(deckResponse.getBody()).isNull();
    }

    @Test
    void shouldSuccessfullyFetchAllDecks() {
        when(deckService.getDecks()).thenReturn(decks);

        ResponseEntity<Iterable<Deck>> deckResponse = deckController.getDecks();

        assertThat(((Collection<?>) deckResponse.getBody()).size()).isEqualTo(decks.size());
        assertThat(deckResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldSuccessfullyFetchNoDecks() {
        this.decks = new ArrayList<>();
        when(deckService.getDecks()).thenReturn(decks);

        ResponseEntity<Iterable<Deck>> deckResponse = deckController.getDecks();

        assertThat(((Collection<?>) deckResponse.getBody()).size()).isEqualTo(0);
        assertThat(deckResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void updateDeck() {
    }

    @Test
    void deleteDeck() {
    }
}