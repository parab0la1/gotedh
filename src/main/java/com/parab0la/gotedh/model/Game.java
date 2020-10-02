package com.parab0la.gotedh.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.parab0la.gotedh.dto.GameDTO;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gameId;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id")
    private Deck winner;

    @ManyToMany
    @JoinTable(
            name = "game_participants",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "deck_id"))
    private List<Deck> participants;

    public Game() {
    }

    public Game(Long gameId, Deck winner, List<Deck> participants) {
        this.gameId = gameId;
        this.winner = winner;
        this.participants = participants;
    }

    public Deck getWinner() {
        return winner;
    }

    public void setWinner(Deck winner) {
        this.winner = winner;
    }

    public List<Deck> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Deck> participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(gameId, game.gameId) &&
                Objects.equals(getWinner(), game.getWinner()) &&
                Objects.equals(getParticipants(), game.getParticipants());
    }

    public GameDTO toGameDTO() {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setParticipants(Deck.toDeckDTOs(this.getParticipants()));
        gameDTO.setWinner(this.getWinner().toDeckDTO());

        return gameDTO;
    }
}
