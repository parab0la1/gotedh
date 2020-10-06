package com.parab0la.gotedh.controller;

import com.parab0la.gotedh.TestRoot;
import com.parab0la.gotedh.dto.DeckDTO;
import com.parab0la.gotedh.dto.GameDTO;
import com.parab0la.gotedh.exception.DeckNotFoundException;
import com.parab0la.gotedh.model.Game;
import com.parab0la.gotedh.service.GameService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class GameControllerTest extends TestRoot {

    @InjectMocks
    GameController gameController;

    @Mock
    GameService gameService;

    @BeforeEach
    void setUp() {
        this.deckDTOs = new ArrayList<>();

        this.deckDTOOne = new DeckDTO();
        this.deckDTOOne.setDeckId(DECK_ID_KALAMAX);
        this.deckDTOs.add(deckDTOOne);

        this.deckDTOTwo = new DeckDTO();
        this.deckDTOTwo.setDeckId(DECK_ID_ANJE);
        this.deckDTOs.add(deckDTOTwo);

        this.deckDTOThree = new DeckDTO();
        this.deckDTOThree.setDeckId(DECK_ID_KENRITH);
        this.deckDTOs.add(deckDTOThree);

        this.deckDTOFour = new DeckDTO();
        this.deckDTOFour.setDeckId(DECK_ID_OMNATH);
        this.deckDTOs.add(deckDTOFour);

        this.gameDTO = new GameDTO();
        this.gameDTO.setParticipants(deckDTOs);
        this.gameDTO.setWinner(deckDTOOne);
    }

    @AfterEach
    void tearDown() {
        this.gameDTO = null;
        this.deckDTO = null;
        this.deckDTOTwo = null;
        this.deckDTOThree = null;
        this.deckDTOFour = null;
    }

    @Test
    void shouldSuccessfullyCreateAGame() {
        Game expectedGame = new Game(1L, this.deckDTOOne.toDeck(), DeckDTO.toDecks(this.deckDTOs));

        when(gameService.createGame(this.gameDTO.toGame())).thenReturn(expectedGame);

        ResponseEntity<GameDTO> gameResponse = gameController.createGame(this.gameDTO);

        assertThat(gameResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(gameResponse.getBody()).isNotNull();
        assertThat(gameResponse.getBody()).isEqualTo(expectedGame.toGameDTO());
    }

    @Test
    void shouldFailWhenCreatingAGame() {
        when(gameService.createGame(this.gameDTO.toGame())).thenThrow(new DeckNotFoundException(PHONY_DECK_ID));

        Exception exception = assertThrows(DeckNotFoundException.class, () -> {
            gameController.createGame(this.gameDTO);
        });

        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(DECK_NOT_FOUND_MSG);
    }
}