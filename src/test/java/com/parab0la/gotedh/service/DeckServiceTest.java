package com.parab0la.gotedh.service;

import com.parab0la.gotedh.TestRoot;
import com.parab0la.gotedh.exception.DeckNotFoundException;
import com.parab0la.gotedh.exception.UserNotFoundException;
import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.User;
import com.parab0la.gotedh.repository.DeckRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class DeckServiceTest extends TestRoot {

    @InjectMocks
    DeckService deckService;

    @Mock
    DeckRepository deckRepository;

    @Mock
    UserService userService;

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
        when(userService.getUser(this.user.getUserId())).thenReturn(user);
        when(deckRepository.save(this.deck)).thenReturn(this.deck);

        Deck actualDeck = deckService.createDeck(this.user.getUserId(), this.deck);

        assertThat(actualDeck).isEqualTo(this.deck);
    }

    @Test
    void shouldFailCreatingADeck() {
        when(userService.getUser(PHONY_USER_ID)).thenThrow(new UserNotFoundException(PHONY_USER_ID));

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            deckService.createDeck(PHONY_USER_ID, this.deck);
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(USER_NOT_FOUND_MSG);
    }

    @Test
    void shouldSuccessfullyGetADeck() {
        when(deckRepository.findById(this.deck.getDeckId())).thenReturn(Optional.of(this.deck));

        Deck deck = deckService.getDeck(this.deck.getDeckId());

        assertThat(deck).isEqualTo(this.deck);
    }

    @Test
    void shouldFailGettingADeck() {
        when(deckRepository.findById(PHONY_DECK_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DeckNotFoundException.class, () -> {
            deckService.getDeck(PHONY_DECK_ID);
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(DECK_NOT_FOUND_MSG);
    }

    @Test
    void shouldSuccessfullyGetAUserDeck() {
        this.deck.setOwner(this.user);

        when(userService.getUser(this.user.getUserId())).thenReturn(user);
        when(deckRepository.findByDeckIdAndOwner(this.deck.getDeckId(), this.user)).thenReturn(Optional.of(this.deck));

        assertThat(deckService.getUserDeck(this.user.getUserId(), this.deck.getDeckId())).isEqualTo(this.deck);
    }

    @Test
    void shouldFailGettingAUserDeckUserNotFound() {
        when(userService.getUser(PHONY_USER_ID)).thenThrow(new UserNotFoundException(PHONY_USER_ID));

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            deckService.getUserDeck(PHONY_USER_ID, this.deck.getDeckId());
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(USER_NOT_FOUND_MSG);

    }

    @Test
    void shouldFailGettingAUserDeckNotFound() {
        when(userService.getUser(this.user.getUserId())).thenReturn(user);
        when(deckRepository.findByDeckIdAndOwner(PHONY_DECK_ID, this.user)).thenThrow(new DeckNotFoundException(PHONY_DECK_ID));

        Exception exception = assertThrows(DeckNotFoundException.class, () -> {
            deckService.getUserDeck(this.user.getUserId(), PHONY_DECK_ID);
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(DECK_NOT_FOUND_MSG);
    }


    @Test
    void shouldSuccessfullyGetUsersDecks() {
        when(userService.getUser(this.user.getUserId())).thenReturn(user);
        when(deckRepository.findByOwner(this.user)).thenReturn(this.decks);

        List<Deck> actualDecks = deckService.getDecks(this.user.getUserId());

        assertThat(actualDecks).isEqualTo(this.decks);
    }

    @Test
    void shouldSuccessfullyGetNoUsersDecks() {
        List<Deck> emptyDeckList = new ArrayList<>();

        when(userService.getUser(user.getUserId())).thenReturn(user);
        when(deckRepository.findByOwner(user)).thenReturn(emptyDeckList);

        List<Deck> actualDecks = deckService.getDecks(user.getUserId());

        assertThat(actualDecks).isEqualTo(emptyDeckList);
    }

    @Test
    void shouldFailGettingUsersDecks() {
        when(userService.getUser(PHONY_USER_ID)).thenThrow(new UserNotFoundException(PHONY_USER_ID));

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            deckService.getDecks(PHONY_USER_ID);
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(USER_NOT_FOUND_MSG);
    }

    @Test
    void shouldSuccessfullyGetAllDecks() {
        when(deckRepository.findAll()).thenReturn(this.decks);

        assertThat(deckService.getDecks()).isEqualTo(this.decks);
    }

    @Test
    void shouldSuccessfullyGetAllDecksNoResult() {
        List<Deck> emptyDeckList = new ArrayList<>();
        when(deckRepository.findAll()).thenReturn(emptyDeckList);

        assertThat(deckService.getDecks()).isEqualTo(emptyDeckList);
    }

    @Test
    void shouldSuccessfullyUpdateADeck() {
        Deck newDeck = new Deck(14124L, "Should not update", 44,
                44, 44, 44, 44, this.user);
        Deck deckFromDB = new Deck(DECK_ID_KALAMAX, KALAMAX, 1,
                1, 1, 1, 1, this.user);
        Deck expectedDeck = new Deck(DECK_ID_KALAMAX, KALAMAX, 44,
                44, 44, 44, 44, this.user);

        when(userService.getUser(this.user.getUserId())).thenReturn(this.user);
        when(deckRepository.findByDeckIdAndOwner(deckFromDB.getDeckId(), this.user)).thenReturn(Optional.of(deckFromDB));
        when(deckRepository.save(expectedDeck)).thenReturn(expectedDeck);

        assertThat(deckService.updateDeck(this.user.getUserId(), deckFromDB.getDeckId(), newDeck)).isEqualTo(expectedDeck);
    }

    @Test
    void shouldFailUpdatingADeckUserNotFound() {
        when(userService.getUser(PHONY_USER_ID)).thenThrow(new UserNotFoundException(PHONY_USER_ID));

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            deckService.updateDeck(PHONY_USER_ID, 1L, new Deck());
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(USER_NOT_FOUND_MSG);
    }

    @Test
    void shouldFailUpdatingADeckNotFound() {
        when(userService.getUser(this.user.getUserId())).thenReturn(this.user);
        when(deckRepository.findByDeckIdAndOwner(PHONY_DECK_ID, this.user)).thenThrow(new DeckNotFoundException(PHONY_DECK_ID));

        Exception exception = assertThrows(DeckNotFoundException.class, () -> {
            deckService.updateDeck(this.user.getUserId(), PHONY_DECK_ID, new Deck());
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(DECK_NOT_FOUND_MSG);
    }

    @Test
    void shouldSuccessfullyDeleteADeck() {
        when(userService.getUser(this.user.getUserId())).thenReturn(user);
        when(deckRepository.findByDeckIdAndOwner(this.deck.getDeckId(), this.user)).thenReturn(Optional.of(this.deck));

        deckService.deleteDeck(this.user.getUserId(), this.deck.getDeckId());
    }

    @Test
    void shouldSuccessfullyDeleteANonExistingDeck() {
        when(userService.getUser(this.user.getUserId())).thenReturn(user);
        when(deckRepository.findByDeckIdAndOwner(PHONY_DECK_ID, this.user)).thenReturn(Optional.empty());

        deckService.deleteDeck(this.user.getUserId(), PHONY_DECK_ID);
    }

    @Test
    void shouldFailDeletingADeck() {
        when(userService.getUser(PHONY_USER_ID)).thenThrow(new UserNotFoundException(PHONY_USER_ID));

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            deckService.deleteDeck(PHONY_USER_ID, this.deck.getDeckId());
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(USER_NOT_FOUND_MSG);
    }
}