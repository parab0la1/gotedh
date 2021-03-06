package com.parab0la.gotedh.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.parab0la.gotedh.dto.DeckDTO;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "decks")
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deckId;
    private String commander;
    private Integer eloRanking;
    private Integer eloChangePerGame;
    private Integer gamesPlayed;
    private Integer gamesWon;
    private Integer gamesWinPercent;
    private Integer oppsWinPercent;
    private Integer podScore;
    private Integer maxPodScore;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @JsonBackReference
    @OneToMany(mappedBy = "winner", cascade = CascadeType.ALL)
    private List<Game> wonGamesList;

    @ManyToMany
    private List<Game> participatedGames;

    public Deck() {
    }

    public Deck(Long deckId, String commander,
                Integer eloRanking, Integer eloChangePerGame,
                Integer gamesPlayed, Integer gamesWinPercent,
                Integer oppsWinPercent, Integer gamesWon,
                Integer podScore, Integer maxPodScore,
                User owner) {
        this.deckId = deckId;
        this.commander = commander;
        this.eloRanking = eloRanking;
        this.eloChangePerGame = eloChangePerGame;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesWinPercent = gamesWinPercent;
        this.oppsWinPercent = oppsWinPercent;
        this.podScore = podScore;
        this.maxPodScore = maxPodScore;
        this.owner = owner;
    }

    public Deck(String commander, Integer eloRanking,
                Integer eloChangePerGame, Integer gamesPlayed,
                Integer gamesWon, Integer gamesWinPercent,
                Integer oppsWinPercent, Integer podScore,
                Integer maxPodScore) {
        this.commander = commander;
        this.eloRanking = eloRanking;
        this.eloChangePerGame = eloChangePerGame;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesWinPercent = gamesWinPercent;
        this.oppsWinPercent = oppsWinPercent;
        this.podScore = podScore;
        this.maxPodScore = maxPodScore;
    }

    public Deck(DeckDTO deckDTO) {
        this.deckId = deckDTO.getDeckId();
        this.commander = deckDTO.getCommander();
        this.eloRanking = deckDTO.getEloRanking();
        this.eloChangePerGame = deckDTO.getEloChangePerGame();
        this.gamesPlayed = deckDTO.getGamesPlayed();
        this.gamesWinPercent = deckDTO.getGamesWinPercent();
        this.oppsWinPercent = deckDTO.getOppsWinPercent();
        if (deckDTO.getOwner() != null) {
            this.owner = deckDTO.getOwner().toUser();
        }
    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long id) {
        this.deckId = id;
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

    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer legacyGamesWon) {
        this.gamesWon = legacyGamesWon;
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

    public List<Game> getWonGamesList() {
        return wonGamesList;
    }

    public void setWonGamesList(List<Game> wonGames) {
        this.wonGamesList = wonGames;
    }

    public List<Game> getParticipatedGames() {
        return participatedGames;
    }

    public void setParticipatedGames(List<Game> participatedGames) {
        this.participatedGames = participatedGames;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getPodScore() {
        return podScore;
    }

    public void setPodScore(Integer podScore) {
        this.podScore = podScore;
    }

    public Integer getMaxPodScore() {
        return maxPodScore;
    }

    public void setMaxPodScore(Integer maxPodScore) {
        this.maxPodScore = maxPodScore;
    }

    public DeckDTO toDeckDTO() {
        return new DeckDTO(this);
    }

    public static List<DeckDTO> toDeckDTOs(List<Deck> decks) {
        return decks.stream().map(deck -> new DeckDTO(deck)).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return Objects.equals(getDeckId(), deck.getDeckId()) &&
                Objects.equals(getCommander(), deck.getCommander()) &&
                Objects.equals(getEloRanking(), deck.getEloRanking()) &&
                Objects.equals(getEloChangePerGame(), deck.getEloChangePerGame()) &&
                Objects.equals(getGamesPlayed(), deck.getGamesPlayed()) &&
                Objects.equals(getGamesWon(), deck.getGamesWon()) &&
                Objects.equals(getGamesWinPercent(), deck.getGamesWinPercent()) &&
                Objects.equals(getOppsWinPercent(), deck.getOppsWinPercent()) &&
                Objects.equals(getPodScore(), deck.getPodScore()) &&
                Objects.equals(getMaxPodScore(), deck.getMaxPodScore()) &&
                Objects.equals(getOwner(), deck.getOwner()) &&
                Objects.equals(getWonGamesList(), deck.getWonGamesList()) &&
                Objects.equals(getParticipatedGames(), deck.getParticipatedGames());
    }

    @Override
    public String toString() {
        return "Deck{" +
                "deckId=" + deckId +
                ", commander='" + commander + '\'' +
                ", eloRanking=" + eloRanking +
                ", eloChangePerGame=" + eloChangePerGame +
                ", gamesPlayed=" + gamesPlayed +
                ", gamesWinPercent=" + gamesWinPercent +
                ", oppsWinPercent=" + oppsWinPercent +
                '}';
    }
}
