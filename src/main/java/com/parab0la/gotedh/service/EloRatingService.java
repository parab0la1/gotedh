package com.parab0la.gotedh.service;

import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.Game;
import com.parab0la.gotedh.model.elo.ELODeck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EloRatingService {

    private static final Logger logger = LoggerFactory.getLogger(EloRatingService.class);

    private List<ELODeck> eloDecks = new ArrayList<>();

    public Game updateGameELORankings(Game game) {
        logger.debug("Calculating ELO ratings for game with winner: {}", game.getWinner().getCommander());

        calculateELOs(game.getParticipants(), game.getWinner().getDeckId());

        updateELORankings(game.getParticipants());
        resetPlayers();

        return game;
    }

    private void calculateELOs(List<Deck> decks, Long winnerId) {
        addPlayers(decks, winnerId);

        for (ELODeck eloDeck : eloDecks) {
            float K = getKValueForDeck(eloDeck);
            logger.debug("K value for deck: {} is: {}", eloDeck.getName(), K);

            int currentPlace = eloDeck.getPlace();
            int currentELO = eloDeck.getEloPre();
            Integer eloChange;

            float s;
            if (currentPlace == 1) {
                s = 1.0F;
            } else {
                s = 0F;
            }

//            Work out EA
            float EA = getCurrentPlayerEA(currentELO) / getOtherPlayersEA();

//            calculate ELO change vs all opponents
            eloChange = Math.round(K * (s - EA));
            eloDeck.setEloPost(eloDeck.getEloPre() + eloChange);

            logger.debug("Final ELO calculated for deck: {} new ELO rating: {}", eloDeck.getName(), eloDeck.getEloPost());
        }

    }

    private void addPlayers(List<Deck> decks, Long winnerId) {
        for (Deck deck : decks) {
            ELODeck eloDeck = new ELODeck();

            eloDeck.setName(deck.getCommander());
            eloDeck.setPlace(deck.getDeckId().equals(winnerId) ? 1 : 2);
            eloDeck.setEloPre(deck.getEloRanking());
            eloDeck.setGamesPlayed(deck.getGamesPlayed());

            logger.debug("Adding deck with name: {} and ELO ranking: {} to ranking list", deck.getCommander(), deck.getEloRanking());

            eloDecks.add(eloDeck);
        }
    }


    private float getCurrentPlayerEA(int currentPlayerELO) {
        double currentPlayerEA = Math.pow(10.0f, currentPlayerELO / 400.0f);

        return (float) currentPlayerEA;
    }

    private float getOtherPlayersEA() {
        float totalEA = 0.0f;

        for (ELODeck eloDeck : eloDecks) {
            totalEA = totalEA + (float) Math.pow(10.0f, eloDeck.getEloPre() / 400.0f);
        }

        return totalEA;
    }

    private void resetPlayers() {
        this.eloDecks = new ArrayList<>();
    }

    private void updateELORankings(List<Deck> decks) {
        for (Deck deck : decks) {
            setELOValues(deck);
        }
    }

    private void setELOValues(Deck deck) {
        for (ELODeck eloDeckDeck : this.eloDecks) {
            if (eloDeckDeck.getName().equals(deck.getCommander())) {

                deck.setEloRanking(eloDeckDeck.getEloPost());
                deck.setEloChangePerGame(calculateEloChangePerGame(eloDeckDeck.getEloPost(), deck.getGamesPlayed()));
                logger.debug("Updating new ELO ranking for deck: {} with elo: {} and ELO change per game: {}",
                        deck.getCommander(),
                        deck.getEloRanking(),
                        deck.getEloChangePerGame());
            }
        }
    }

    private Integer calculateEloChangePerGame(Integer elo, Integer gamesPlayed) {
        return (elo - 1000) / gamesPlayed;
    }

    private float getKValueForDeck(ELODeck deck) {
        float k;

        if (deck.getEloPre() > 2400) {
            k = 10;
        } else {
            k = deck.getGamesPlayed() < 30 ? 40 : 20;
        }

        return k;

    }

}
