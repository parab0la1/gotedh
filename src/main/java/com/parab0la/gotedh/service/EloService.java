package com.parab0la.gotedh.service;

import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.elo.ELODeck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EloService {

    private static final Logger logger = LoggerFactory.getLogger(EloService.class);

    private List<ELODeck> eloDecks = new ArrayList<>();

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

    public void calculateELOs(List<Deck> decks, Long winnerId) {
        addPlayers(decks, winnerId);

        for (int i = 0; i < eloDecks.size(); i++) {
            float K = getKValueForDeck(eloDecks.get(i), eloDecks.size());
            logger.debug("K value for deck: {} is: {}", eloDecks.get(i).getName(), K);

            int currentPlace = eloDecks.get(i).getPlace();
            int currentELO = eloDecks.get(i).getEloPre();
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
            eloDecks.get(i).setEloPost(eloDecks.get(i).getEloPre() + eloChange);

            logger.debug("Final ELO calculated for deck: {} new ELO rating: {}", eloDecks.get(i).getName(), eloDecks.get(i).getEloPost());
        }

    }

    private float getCurrentPlayerEA(int currentPlayerELO) {
        double currentPlayerEA = Math.pow(10.0f, currentPlayerELO / 400.0f);

        return (float) currentPlayerEA;
    }

    private float getOtherPlayersEA() {
        float totalEA = 0.0f;

        for (int i = 0; i < eloDecks.size(); i++) {
            totalEA = totalEA + (float) Math.pow(10.0f, eloDecks.get(i).getEloPre() / 400.0f);
        }

        return totalEA;
    }

    public void resetPlayers() {
        this.eloDecks = new ArrayList<>();
    }

    public void updateELORankings(List<Deck> decks) {
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

    private float getKValueForDeck(ELODeck deck, Integer playerCount) {
        float k;

        if (deck.getEloPre() > 2400) {
            k = 10;
        } else {
            k = deck.getGamesPlayed() < 30 ? 40 : 20;
        }

        return k;

    }

}
