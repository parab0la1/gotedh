package com.parab0la.gotedh.service;

import com.parab0la.gotedh.TestRoot;
import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.Game;
import com.parab0la.gotedh.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class EloRatingServiceTest extends TestRoot {

    @InjectMocks
    EloRatingService eloRatingService;

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();

        this.deck = new Deck(DECK_ID_KALAMAX, KALAMAX, 1097, 15, 7,
                67, 60, 2, 6, 10, new User());

        this.deckTwo = new Deck(DECK_ID_ANJE, ANJE, 1094, 15, 27,
                33, 30, 1, 3, 10, new User());

        this.deckThree = new Deck(DECK_ID_KENRITH, KENRITH, 1082, 15, 6,
                33, 30, 1, 3, 10, new User());

        this.deckFour = new Deck(DECK_ID_OMNATH, OMNATH, 1076, 15, 4,
                33, 30, 1, 3, 10, new User());

        this.decks = new ArrayList<>();
        this.decks.add(deck);
        this.decks.add(deckTwo);
        this.decks.add(deckThree);
        this.decks.add(deckFour);

        game.setParticipants(this.decks);
        game.setWinner(this.deck);
    }

    @AfterEach
    void tearDown() {
        this.game = null;
        this.deck = null;
        this.deckTwo = null;
        this.deckThree = null;
        this.deckFour = null;
    }

    @Test
    void updateGameELORankingsTopGame() {
        Game expectedGame = new Game();
        List<Deck> expectedParticipants = new ArrayList<>();

//        Set expected decks with correct updated ELO
        Deck expectedDeckOne = new Deck(DECK_ID_KALAMAX, KALAMAX, 1126, 18, 7,
                67, 60, 2, 6, 10, new User());

        Deck expectedDeckTwo = new Deck(DECK_ID_ANJE, ANJE, 1084, 3, 27,
                33, 30, 1, 3, 10, new User());

        Deck expectedDeckThree = new Deck(DECK_ID_KENRITH, KENRITH, 1072, 12, 6,
                33, 30, 1, 3, 10, new User());

        Deck expectedDeckFour = new Deck(DECK_ID_OMNATH, OMNATH, 1067, 16, 4,
                33, 30, 1, 3, 10, new User());

        expectedParticipants.add(expectedDeckOne);
        expectedParticipants.add(expectedDeckTwo);
        expectedParticipants.add(expectedDeckThree);
        expectedParticipants.add(expectedDeckFour);

        expectedGame.setParticipants(expectedParticipants);
        expectedGame.setWinner(expectedDeckOne);

        Game actualGame = eloRatingService.updateGameELORankings(game);

        assertThat(actualGame).isEqualTo(expectedGame);
    }

    @Test
    void updateGameELORankingsSpreadGameLowDeckWin() {
//        Set input decks ELO to new ratings
        game.getParticipants().get(0).setEloRanking(1094);
        game.getParticipants().get(1).setEloRanking(1054);
        game.getParticipants().get(2).setEloRanking(1046);
        game.getParticipants().get(3).setEloRanking(931);
//        Set the winner to a deck with very low ELO rating
        game.setWinner(this.deckFour);

        Game expectedGame = new Game();
        List<Deck> expectedParticipants = new ArrayList<>();

//        Set expected decks with correct updated ELO
        Deck expectedDeckOne = new Deck(DECK_ID_KALAMAX, KALAMAX, 1080, 11, 7,
                67, 60, 2, 6, 10, new User());

        Deck expectedDeckTwo = new Deck(DECK_ID_ANJE, ANJE, 1043, 1, 27,
                33, 30, 1, 3, 10, new User());

        Deck expectedDeckThree = new Deck(DECK_ID_KENRITH, KENRITH, 1036, 6, 6,
                33, 30, 1, 3, 10, new User());

        Deck expectedDeckFour = new Deck(DECK_ID_OMNATH, OMNATH, 966, -8, 4,
                33, 30, 1, 3, 10, new User());

        expectedParticipants.add(expectedDeckOne);
        expectedParticipants.add(expectedDeckTwo);
        expectedParticipants.add(expectedDeckThree);
        expectedParticipants.add(expectedDeckFour);

        expectedGame.setParticipants(expectedParticipants);
        expectedGame.setWinner(expectedDeckFour);

        Game actualGame = eloRatingService.updateGameELORankings(game);

        assertThat(actualGame).isEqualTo(expectedGame);
    }

    @Test
    void updateGameELORankingsSpreadGameHighDeckWin() {
//        Set input decks ELO to new ratings
        game.getParticipants().get(0).setEloRanking(1094);
        game.getParticipants().get(1).setEloRanking(1054);
        game.getParticipants().get(2).setEloRanking(1046);
        game.getParticipants().get(3).setEloRanking(931);
//        Set the winner to a deck with very low ELO rating
        game.setWinner(this.deck);

        Game expectedGame = new Game();
        List<Deck> expectedParticipants = new ArrayList<>();

//        Set expected decks with correct updated ELO
        Deck expectedDeckOne = new Deck(DECK_ID_KALAMAX, KALAMAX, 1120, 17, 7,
                67, 60, 2, 6, 10, new User());

        Deck expectedDeckTwo = new Deck(DECK_ID_ANJE, ANJE, 1043, 1, 27,
                33, 30, 1, 3, 10, new User());

        Deck expectedDeckThree = new Deck(DECK_ID_KENRITH, KENRITH, 1036, 6, 6,
                33, 30, 1, 3, 10, new User());

        Deck expectedDeckFour = new Deck(DECK_ID_OMNATH, OMNATH, 926, -18, 4,
                33, 30, 1, 3, 10, new User());

        expectedParticipants.add(expectedDeckOne);
        expectedParticipants.add(expectedDeckTwo);
        expectedParticipants.add(expectedDeckThree);
        expectedParticipants.add(expectedDeckFour);

        expectedGame.setParticipants(expectedParticipants);
        expectedGame.setWinner(expectedDeckOne);

        Game actualGame = eloRatingService.updateGameELORankings(game);

        assertThat(actualGame).isEqualTo(expectedGame);
    }
}