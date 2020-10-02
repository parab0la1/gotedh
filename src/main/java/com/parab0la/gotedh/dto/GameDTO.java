package com.parab0la.gotedh.dto;

import com.parab0la.gotedh.model.Deck;
import com.parab0la.gotedh.model.Game;

import java.util.List;
import java.util.Objects;

public class GameDTO {

    private DeckDTO winner;
    private List<DeckDTO> participants;

    public DeckDTO getWinner() {
        return winner;
    }

    public void setWinner(DeckDTO winner) {
        this.winner = winner;
    }

    public List<DeckDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<DeckDTO> participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDTO gameDTO = (GameDTO) o;
        return Objects.equals(getWinner(), gameDTO.getWinner()) &&
                Objects.equals(getParticipants(), gameDTO.getParticipants());
    }

    public Game toGame() {
        Game game = new Game();
        game.setParticipants(DeckDTO.toDecks(this.participants));
        game.setWinner(new Deck(this.getWinner()));

        return game;

    }
}
