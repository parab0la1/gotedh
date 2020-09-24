package com.parab0la.gotedh.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.parab0la.gotedh.model.Deck;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeckDTO {

    private Long deckId;
    private String commander;
    private Integer eloRanking;
    private Integer eloChangePerGame;
    private Integer gamesPlayed;
    private Integer gamesWinPercent;
    private Integer oppsWinPercent;
    private UserDTO owner;

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

        deck.getOwner().setDecks(null);
        if (deck.getOwner() != null) {
            this.owner = new UserDTO(deck.getOwner());
        }

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

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public static List<DeckDTO> toDeckDTOs(List<Deck> decks) {
        return decks.stream().map(deck -> new DeckDTO(deck)).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeckDTO deckDTO = (DeckDTO) o;
        return Objects.equals(getDeckId(), deckDTO.getDeckId()) &&
                Objects.equals(getCommander(), deckDTO.getCommander()) &&
                Objects.equals(getEloRanking(), deckDTO.getEloRanking()) &&
                Objects.equals(getEloChangePerGame(), deckDTO.getEloChangePerGame()) &&
                Objects.equals(getGamesPlayed(), deckDTO.getGamesPlayed()) &&
                Objects.equals(getGamesWinPercent(), deckDTO.getGamesWinPercent()) &&
                Objects.equals(getOppsWinPercent(), deckDTO.getOppsWinPercent()) &&
                Objects.equals(getOwner(), deckDTO.getOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeckId(), getCommander(), getEloRanking(), getEloChangePerGame(), getGamesPlayed(), getGamesWinPercent(), getOppsWinPercent(), getOwner());
    }
}
