package com.parab0la.gotedh.controller;

import com.parab0la.gotedh.dto.GameDTO;
import com.parab0la.gotedh.model.Game;
import com.parab0la.gotedh.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameDTO> createGame(@RequestBody GameDTO gameDTO) {
        Game game = gameDTO.toGame();

        return new ResponseEntity<>(gameService.createGame(game).toGameDTO(), HttpStatus.CREATED);
    }
}
