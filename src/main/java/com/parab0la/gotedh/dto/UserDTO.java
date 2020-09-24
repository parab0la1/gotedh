package com.parab0la.gotedh.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.parab0la.gotedh.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long userId;
    private String name;
    private Integer eloRanking;
    private Integer gamesPlayed;
    private Integer gamesWinPercent;
    private Integer oppsWinPercent;
    private List<DeckDTO> decks;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.eloRanking = user.getEloRanking();
        this.gamesPlayed = user.getGamesPlayed();
        this.gamesWinPercent = user.getGamesWinPercent();
        this.oppsWinPercent = user.getOppsWinPercent();

        if (user.getDecks() != null) {
            this.decks = DeckDTO.toDeckDTOs(new ArrayList<>(user.getDecks()));
        }
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

    public List<DeckDTO> getDecks() {
        return decks;
    }

    public void setDecks(List<DeckDTO> decks) {
        this.decks = decks;
    }

    public static List<UserDTO> toUserDTOs(List<User> users) {
        return users.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(getUserId(), userDTO.getUserId()) &&
                Objects.equals(getName(), userDTO.getName()) &&
                Objects.equals(getEloRanking(), userDTO.getEloRanking()) &&
                Objects.equals(getGamesPlayed(), userDTO.getGamesPlayed()) &&
                Objects.equals(getGamesWinPercent(), userDTO.getGamesWinPercent()) &&
                Objects.equals(getOppsWinPercent(), userDTO.getOppsWinPercent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getName(), getEloRanking(), getGamesPlayed(), getGamesWinPercent(), getOppsWinPercent());
    }
}
