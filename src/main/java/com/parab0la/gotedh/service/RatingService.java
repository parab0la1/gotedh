package com.parab0la.gotedh.service;

import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);

    public Game updateRankings(Game game) {
        logger.debug("Calculating ratings for game with winner: {}", game.getWinner().getCommander());
        return updateGameValues(game);
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
        float winPercent = deck.getGamesWon().floatValue() / deck.getGamesPlayed().floatValue();

        deck.setGamesWinPercent(Math.round(winPercent * 100));

        logger.debug("Updating win % for deck: {}, {}, new win %: {}",
                deck.getDeckId(), deck.getCommander(), Math.round(winPercent * 100));

        deck.setGamesWinPercent(Math.round(winPercent * 100));
    }
}
