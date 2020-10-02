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

        Game game = updateRankings(populatedGame);

        return gameRepository.save(game);
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

    private Game updateRankings(Game game) {
        Game updatedGame = updateGameValues(game);

        return updateELORankings(updatedGame);
    }

    private Game updateGameValues(Game game) {
        game.getParticipants().forEach(deck -> {
            deck.setGamesPlayed(deck.getGamesPlayed() + 1);
            updateOppsWinPercent(game, deck);
            updateGamesWinPercent(deck);
        });

        return game;
    }

    private void updateOppsWinPercent(Game game, Deck deck) {
        Integer podScore = game.getParticipants().size() - 1;

        if (deck.getDeckId().equals(game.getWinner().getDeckId())) {
            deck.setGamesWon(deck.getGamesWon() + 1);

//            If the deck is the winner, it receives the podscore for the game
            Integer deckPodScore = deck.getPodScore() + (podScore);
            deck.setPodScore(deckPodScore);
        }

        deck.setMaxPodScore(deck.getMaxPodScore() + podScore);

        float winPercent = deck.getPodScore().floatValue() / deck.getMaxPodScore().floatValue();

        deck.setOppsWinPercent(Math.round(winPercent * 100));

        logger.debug("Updating opponent win % for deck: {}, {}, new opps win %: {}",
                deck.getDeckId(), deck.getCommander(), deck.getOppsWinPercent());
    }

    private void updateGamesWinPercent(Deck deck) {
        float winPercent = deck.getWonGamesList().size() / deck.getGamesPlayed().floatValue();

        deck.setGamesWinPercent(Math.round(winPercent * 100));

        logger.debug("Updating win % for deck: {}, {}, new win %: {}",
                deck.getDeckId(), deck.getCommander(), Math.round(winPercent * 100));

        deck.setGamesWinPercent(Math.round(winPercent * 100));
    }

    private Game updateELORankings(Game game) {
        eloService.calculateELOs(game.getParticipants(), game.getWinner().getDeckId());

        eloService.updateELORankings(game.getParticipants());
        eloService.resetPlayers();

        return game;
    }
}
