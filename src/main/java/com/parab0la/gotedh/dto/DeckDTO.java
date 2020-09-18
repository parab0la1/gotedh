package com.parab0la.gotedh.dto;

import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.User;

public class DeckDTO {

    private Long deckId;
    private String commander;
    private Integer eloRanking;
    private Integer eloChangePerGame;
    private Integer gamesPlayed;
    private Integer gamesWinPercent;
    private Integer oppsWinPercent;
    private User owner;

    public DeckDTO() {
    }

    public DeckDTO(Deck deck) {
        this.deckId = deck.getDeckId();
        this.commander = deck.getCommander();
        this.eloRanking = deck.getEloRanking();
        this.eloChangePerGame = deck.getEloChangePerGame();
        this.gamesPlayed = deck.getGamesPlayed();
        this.gamesWinPercent = deck.getGamesWinPercent();
        this.oppsWinPercent = deck.getOppsWinPercent();
        this.owner = deck.getOwner();
    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public String getCommander() {
        return commander;
    }

    public void setCommander(String commander) {
        this.commander = commander;
    }

    public Integer getEloRanking() {
        return eloRanking;
    }

    public void setEloRanking(Integer eloRanking) {
        this.eloRanking = eloRanking;
    }

    public Integer getEloChangePerGame() {
        return eloChangePerGame;
    }

    public void setEloChangePerGame(Integer eloChangePerGame) {
        this.eloChangePerGame = eloChangePerGame;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Integer getGamesWinPercent() {
        return gamesWinPercent;
    }

    public void setGamesWinPercent(Integer gamesWinPercent) {
        this.gamesWinPercent = gamesWinPercent;
    }

    public Integer getOppsWinPercent() {
        return oppsWinPercent;
    }

    public void setOppsWinPercent(Integer oppsWinPercent) {
        this.oppsWinPercent = oppsWinPercent;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
