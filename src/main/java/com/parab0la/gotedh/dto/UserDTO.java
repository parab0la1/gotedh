package com.parab0la.gotedh.dto;

import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.User;

import java.util.Set;

public class UserDTO {

    private Long userId;
    private String name;
    private Integer eloRanking;
    private Integer gamesPlayed;
    private Integer gamesWinPercent;
    private Integer oppsWinPercent;
    private Set<Deck> decks;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.eloRanking = user.getEloRanking();
        this.gamesPlayed = user.getGamesPlayed();
        this.gamesWinPercent = user.getGamesWinPercent();
        this.oppsWinPercent = user.getOppsWinPercent();
        this.decks = user.getDecks();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEloRanking() {
        return eloRanking;
    }

    public void setEloRanking(Integer eloRanking) {
        this.eloRanking = eloRanking;
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

    public Set<Deck> getDecks() {
        return decks;
    }

    public void setDecks(Set<Deck> decks) {
        this.decks = decks;
    }
}
