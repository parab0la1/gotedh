package com.parab0la.gotedh.service;

import com.parab0la.gotedh.TestRoot;
import com.parab0la.gotedh.exception.DeckNotFoundException;
import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.Game;
import com.parab0la.gotedh.model.User;
import com.parab0la.gotedh.repository.GameRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class GameServiceTest extends TestRoot {

    @InjectMocks
    GameService gameService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private DeckService deckService;

    @Mock
    private EloService eloService;

    @BeforeEach
    void setUp() {

        this.user = new User(USER_ID, "Joel Nilsson", 1,
                5, 56, 46, new HashSet<>());

        this.deck = new Deck(
                DECK_ID_KALAMAX, KALAMAX, 1000,
                15, 2, 50,
                50, 1, 3,
                7, new User()
        );
        this.deckTwo = new Deck(
                DECK_ID_ANJE, ANJE, 1000,
                15, 2, 50,
                50, 1, 3,
                7, new User()
        );
        this.deckThree = new Deck(
                DECK_ID_KENRITH, KENRITH, 1000,
                15, 2, 50,
                50, 1, 3,
                7, new User()
        );
        this.deckFour = new Deck(
                DECK_ID_OMNATH, OMNATH, 1000,
                15, 2, 50,
                50, 1, 3,
                7, new User()
        );

        this.decks = new ArrayList<>();
        this.decks.add(deck);
        this.decks.add(deckTwo);
        this.decks.add(deckThree);

        Deck deckInputOne = new Deck();
        deckInputOne.setDeckId(DECK_ID_KALAMAX);
        Deck deckInputTwo = new Deck();
        deckInputTwo.setDeckId(DECK_ID_ANJE);
        Deck deckInputThree = new Deck();
        deckInputThree.setDeckId(DECK_ID_KENRITH);
        Deck deckInputFour = new Deck();
        deckInputFour.setDeckId(DECK_ID_OMNATH);

        List<Deck> participants = new ArrayList<>();
        participants.add(deckInputOne);
        participants.add(deckInputTwo);
        participants.add(deckInputThree);
        participants.add(deckInputFour);

        this.game = new Game();
        game.setParticipants(participants);
        game.setWinner(deckInputOne);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldFailCreatingAGameDeckNotFound() {
        when(deckService.getDeck(DECK_ID_KALAMAX)).thenThrow(new DeckNotFoundException(DECK_ID_KALAMAX));

        Exception exception = assertThrows(DeckNotFoundException.class, () -> {
            gameService.createGame(this.game);
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(KALAMAX_DECK_NOT_FOUND_MSG);
    }

    @Test
    void shouldSuccessfullyCreateAGame() {
    }
}