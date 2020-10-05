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
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class RatingServiceTest extends TestRoot {

    @InjectMocks
    RatingService ratingService;


    @BeforeEach
    void setUp() {
        this.user = new User(USER_ID, "Joel Nilsson", 1,
                5, 56, 46, new HashSet<>());

        this.deck = new Deck(
                DECK_ID_KALAMAX, KALAMAX, 1126,
                15, 2, 50,
                50, 1, 3,
                7, new User()
        );

        this.deckTwo = new Deck(
                DECK_ID_ANJE, ANJE, 1084,
                15, 2, 50,
                50, 1, 3,
                7, new User()
        );

        this.deckThree = new Deck(
                DECK_ID_KENRITH, KENRITH, 1072,
                15, 2, 50,
                50, 1, 3,
                7, new User()
        );

        this.deckFour = new Deck(
                DECK_ID_OMNATH, OMNATH, 1067,
                15, 2, 50,
                50, 1, 3,
                7, new User()
        );

        this.decks = new ArrayList<>();
        this.decks.add(deck);
        this.decks.add(deckTwo);
        this.decks.add(deckThree);
        this.decks.add(deckFour);


        this.game = new Game();
        game.setParticipants(this.decks);
        game.setWinner(this.deck);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void updateRankings() {
        Game expectedGame = new Game();
        List<Deck> expectedParticipants = new ArrayList<>();

        //        Expected decks with new stats (Games played, game winner, win %)
        Deck expectedDeckOne = new Deck(
                DECK_ID_KALAMAX, KALAMAX, 1126,
                15, 3, 67,
                60, 2, 6,
                10, new User()
        );

        Deck expectedDeckTwo = new Deck(
                DECK_ID_ANJE, ANJE, 1084,
                15, 3, 33,
                30, 1, 3,
                10, new User()
        );

        Deck expectedDeckThree = new Deck(
                DECK_ID_KENRITH, KENRITH, 1072,
                15, 3, 33,
                30, 1, 3,
                10, new User()
        );

        Deck expectedDeckFour = new Deck(
                DECK_ID_OMNATH, OMNATH, 1067,
                15, 3, 33,
                30, 1, 3,
                10, new User()
        );

        expectedParticipants.add(expectedDeckOne);
        expectedParticipants.add(expectedDeckTwo);
        expectedParticipants.add(expectedDeckThree);
        expectedParticipants.add(expectedDeckFour);

        expectedGame.setParticipants(expectedParticipants);
        expectedGame.setWinner(expectedDeckOne);

        Game actualGame = ratingService.updateRankings(game);

        assertThat(actualGame).isEqualTo(expectedGame);
    }
}