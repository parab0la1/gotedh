package com.parab0la.gotedh.controller;

import com.parab0la.gotedh.dto.DeckDTO;
import com.parab0la.gotedh.exception.DeckNotFoundException;
import com.parab0la.gotedh.exception.UserNotFoundException;
import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.User;
import com.parab0la.gotedh.service.DeckService;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class DeckControllerTest {

    @InjectMocks
    DeckController deckController;

    @Mock
    private DeckService deckService;

    private List<Deck> decks;
    private Deck deck;
    private Deck deckTwo;
    private Deck deckThree;
    private User user;
    private final String KALAMAX = "Kalamax";
    private final String ANJE = "Anje";
    private final String KENRITH = "Kenrith";
    private final Long PHONY_USER_ID = 457L;
    private final Long USER_ID = 1L;
    private final Long DECK_ID_KALAMAX = 1L;
    private final String USER_NOT_FOUND_MSG = "The user with id: " + PHONY_USER_ID + " could not be found";
    private final String DECK_NOT_FOUND_MSG = "The deck with id: " + DECK_ID_KALAMAX + " could not be found";

    @BeforeEach
    void setUp() {
        this.user = new User(USER_ID, "Joel Nilsson", 1,
                5, 56, 46, new HashSet<>());

        this.deck = new Deck(DECK_ID_KALAMAX, KALAMAX, 1,
                2, 2, 2, 2, new User());
        this.deckTwo = new Deck(2L, ANJE, 1,
                2, 2, 2, 2, new User());
        this.deckThree = new Deck(3L, KENRITH, 1,
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
    void shouldSuccessfullyCreateADeck() {
        when(deckService.createDeck(this.user.getUserId(), this.deck)).thenReturn(this.deck);

        ResponseEntity<DeckDTO> deckResponseOne = deckController.createDeck(this.user.getUserId(), this.deck);

        assertThat(deckResponseOne.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(deckResponseOne.getBody()).isNotNull();
        assertThat(deckResponseOne.getBody().getCommander()).isEqualTo(KALAMAX);
    }

    @Test
    void shouldFailCreatingADeck() {
        when(deckService.createDeck(PHONY_USER_ID, this.deck)).thenThrow(new UserNotFoundException(PHONY_USER_ID));

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            deckController.createDeck(PHONY_USER_ID, this.deck);
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(USER_NOT_FOUND_MSG);
    }

    @Test
    void shouldSuccessfullyFetchADeck() {
        when(deckService.getDeck(DECK_ID_KALAMAX)).thenReturn(Optional.of(this.deck));
        when(deckService.getDeck(2L)).thenReturn(Optional.of(this.deckTwo));
        when(deckService.getDeck(3L)).thenReturn(Optional.of(this.deckThree));

        ResponseEntity<DeckDTO> deckResponseOne = deckController.getDeck(DECK_ID_KALAMAX);
        ResponseEntity<DeckDTO> deckResponseTwo = deckController.getDeck(2L);
        ResponseEntity<DeckDTO> deckResponseThree = deckController.getDeck(3L);

        assertThat(deckResponseOne.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deckResponseTwo.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deckResponseThree.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(deckResponseOne.getBody()).isNotNull();
        assertThat(deckResponseOne.getBody()).isNotNull();
        assertThat(deckResponseTwo.getBody()).isNotNull();
        assertThat(deckResponseThree.getBody()).isNotNull();

        assertEquals(deckResponseOne.getBody(), new DeckDTO(this.deck));
        assertEquals(deckResponseTwo.getBody(), new DeckDTO(this.deckTwo));
        assertEquals(deckResponseThree.getBody(), new DeckDTO(this.deckThree));
    }

    @Test
    void shouldFailFindingFetchADeck() {
        Optional<Deck> noDeck = Optional.empty();
        when(deckService.getDeck(4L)).thenReturn(noDeck);

        ResponseEntity<DeckDTO> deckResponse = deckController.getDeck(4L);

        assertThat(deckResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(deckResponse.getBody()).isNull();
    }

    @Test
    void shouldSuccessfullyFetchAUserDeck() {
        when(deckService.getUserDeck(this.user.getUserId(), DECK_ID_KALAMAX)).thenReturn(this.deck);

        ResponseEntity<DeckDTO> deckResponse = deckController.getUserDeck(this.user.getUserId(), DECK_ID_KALAMAX);

        assertThat(deckResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deckResponse.getBody()).isNotNull();
        assertEquals(deckResponse.getBody(), new DeckDTO(this.deck));
    }

    @Test
    void shouldFailFetchingAUserDeck() {
        when(deckService.getUserDeck(PHONY_USER_ID, this.deck.getDeckId())).thenThrow(new UserNotFoundException(PHONY_USER_ID));

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            deckController.getUserDeck(PHONY_USER_ID, this.deck.getDeckId());
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(USER_NOT_FOUND_MSG);
    }

    @Test
    void shouldSuccessfullyFetchAllDecks() {
        when(deckService.getDecks()).thenReturn(decks);

        ResponseEntity<List<DeckDTO>> deckResponse = deckController.getDecks();

        assertThat(deckResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deckResponse.getBody()).isNotNull();
        assertThat((deckResponse.getBody()).size()).isEqualTo(decks.size());
        assertEquals(DeckDTO.toDeckDTOs(this.decks), deckResponse.getBody());
    }

    @Test
    void shouldSuccessfullyFetchNoDecks() {
        this.decks = new ArrayList<>();
        when(deckService.getDecks()).thenReturn(decks);

        ResponseEntity<List<DeckDTO>> deckResponse = deckController.getDecks();

        assertThat(deckResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deckResponse.getBody()).isNotNull();
        assertThat(((Collection<?>) deckResponse.getBody()).size()).isEqualTo(0);
    }

    @Test
    void shouldSuccessfullyFetchUserDecks() {
        when(deckService.getUserDecks(USER_ID)).thenReturn(decks);

        ResponseEntity<List<DeckDTO>> deckResponse = deckController.getUserDecks(USER_ID);

        assertThat(deckResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deckResponse.getBody()).isNotNull();
        assertThat((deckResponse.getBody()).size()).isEqualTo(decks.size());

        assertEquals(DeckDTO.toDeckDTOs(this.decks), deckResponse.getBody());
    }

    @Test
    void shouldFailFetchingUserDecks() {
        when(deckService.getUserDecks(PHONY_USER_ID)).thenThrow(new UserNotFoundException(PHONY_USER_ID));

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            deckController.getUserDecks(PHONY_USER_ID);
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(USER_NOT_FOUND_MSG);
    }


    @Test
    void shouldSuccessfullyUpdateDeck() {
        Deck updatedDeck = new Deck(DECK_ID_KALAMAX, "Not-change Changesson", 2,
                3, 3, 3, 3, new User());

        when(deckService.updateDeck(this.user.getUserId(), DECK_ID_KALAMAX, updatedDeck)).thenReturn(
                new Deck(DECK_ID_KALAMAX, KALAMAX, 2, 3,
                        3, 3, 3, new User()
                ));

        ResponseEntity<DeckDTO> deckResponse = deckController.updateDeck(this.user.getUserId(), DECK_ID_KALAMAX, updatedDeck);

        assertThat(deckResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deckResponse.getBody()).isNotNull();
        assertEquals(new DeckDTO(
                new Deck(DECK_ID_KALAMAX, KALAMAX,
                        2, 3, 3,
                        3, 3, new User()
                )), deckResponse.getBody());
    }

    @Test
    void shouldFailUpdatingDeck() {
        when(deckService.updateDeck(any(), any(), any())).thenReturn(null);

        ResponseEntity<DeckDTO> deckResponse = deckController.updateDeck(this.user.getUserId(), DECK_ID_KALAMAX, new Deck());

        assertThat(deckResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(deckResponse.getBody()).isNull();
    }

    @Test
    void shouldSuccessfullyDeleteDeck() {
        doNothing().when(deckService).deleteDeck(this.user.getUserId(), this.deck.getDeckId());

        ResponseEntity<String> deckResponse = deckController.deleteDeck(this.user.getUserId(), this.deck.getDeckId());

        assertThat(deckResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldFailDeletingADeckUserNotFound() {
        doThrow(new UserNotFoundException(PHONY_USER_ID)).when(deckService).deleteDeck(PHONY_USER_ID, this.deck.getDeckId());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            deckController.deleteDeck(PHONY_USER_ID, this.deck.getDeckId());
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(USER_NOT_FOUND_MSG);
    }

    @Test
    void shouldFailDeletingADeckNotFound() {
        doThrow(new DeckNotFoundException(this.deck.getDeckId())).when(deckService).deleteDeck(this.user.getUserId(), this.deck.getDeckId());

        Exception exception = assertThrows(DeckNotFoundException.class, () -> {
            deckController.deleteDeck(this.user.getUserId(), this.deck.getDeckId());
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(DECK_NOT_FOUND_MSG);
    }
}