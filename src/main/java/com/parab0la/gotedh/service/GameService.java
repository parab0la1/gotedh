package com.parab0la.gotedh.service;

import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.Game;
import com.parab0la.gotedh.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;
    private final DeckService deckService;
    private final EloService eloService;

    public GameService(EloService eloService, GameRepository gameRepository, DeckService deckService) {
        this.eloService = eloService;
        this.gameRepository = gameRepository;
        this.deckService = deckService;
    }

    public Game createGame(Game gameInput) {
        Game populatedGame = populateGame(gameInput);

        logger.debug("Populated a game with the winner deck (id) (name): {}, {}",
                populatedGame.getWinner().getDeckId(),
                populatedGame.getWinner().getCommander());

        updateELORankings(populatedGame);

        return gameRepository.save(populatedGame);
    }

    private Game populateGame(Game gameInput) {
        Game game = new Game();

        game.setParticipants(populateParticipants(gameInput));
        game.setWinner(deckService.getDeck(gameInput.getWinner().getDeckId()));

        return game;
    }

    private List<Deck> populateParticipants(Game gameInput) {
        return gameInput.getParticipants().stream().map(deckParticipant -> deckService.getDeck(deckParticipant.getDeckId())).collect(Collectors.toList());
    }

    private Game updateELORankings(Game game) {
        eloService.calculateELOs(game.getParticipants(), game.getWinner().getDeckId());

        eloService.updateELORankings(game.getParticipants());
        eloService.resetPlayers();

        return game;
    }
}
