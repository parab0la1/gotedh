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
    private final EloRatingService eloRatingService;
    private final RatingService ratingService;

    public GameService(EloRatingService eloRatingService, GameRepository gameRepository,
                       DeckService deckService, RatingService ratingService) {
        this.eloRatingService = eloRatingService;
        this.gameRepository = gameRepository;
        this.deckService = deckService;
        this.ratingService = ratingService;
    }

    public Game createGame(Game gameInput) {
        Game populatedGame = populateGame(gameInput);

        logger.debug("Populated a game with the winner deck (id) (name): {}, {}",
                populatedGame.getWinner().getDeckId(),
                populatedGame.getWinner().getCommander());

        Game updatedEloGame = updateGameELORankings(populatedGame);
        Game updatedRatingGame = updateGameRankings(updatedEloGame);

        return gameRepository.save(updatedRatingGame);
    }

    private Game populateGame(Game gameInput) {
        gameInput.setParticipants(populateParticipants(gameInput));
        gameInput.setWinner(deckService.getDeck(gameInput.getWinner().getDeckId()));

        return gameInput;
    }

    private List<Deck> populateParticipants(Game gameInput) {
        return gameInput.getParticipants().stream().map(deckParticipant -> deckService.getDeck(deckParticipant.getDeckId())).collect(Collectors.toList());
    }

    private Game updateGameRankings(Game game) {
        return ratingService.updateRankings(game);
    }

    private Game updateGameELORankings(Game game) {
        return eloRatingService.updateGameELORankings(game);
    }
}
