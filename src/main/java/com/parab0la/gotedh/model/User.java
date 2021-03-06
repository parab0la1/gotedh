package com.parab0la.gotedh.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.parab0la.gotedh.dto.UserDTO;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String name;
    private Integer eloRanking;
    private Integer gamesPlayed;
    private Integer gamesWinPercent;
    private Integer oppsWinPercent;

    @JsonBackReference
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Deck> decks = new HashSet<>();

    public User() {
    }

    public User(Long userId, String name, Integer eloRanking,
                Integer gamesPlayed, Integer gamesWinPercent,
                Integer oppsWinPercent, Set<Deck> decks) {
        this.userId = userId;
        this.name = name;
        this.eloRanking = eloRanking;
        this.gamesPlayed = gamesPlayed;
        this.gamesWinPercent = gamesWinPercent;
        this.oppsWinPercent = oppsWinPercent;
        this.decks = decks;
    }

    public User(String name, Integer eloRanking, Integer gamesPlayed, Integer gamesWinPercent, Integer oppsWinPercent) {
        this.name = name;
        this.eloRanking = eloRanking;
        this.gamesPlayed = gamesPlayed;
        this.gamesWinPercent = gamesWinPercent;
        this.oppsWinPercent = oppsWinPercent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = id;
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

    public UserDTO toUserDTO() {
        return new UserDTO(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getUserId(), user.getUserId()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getEloRanking(), user.getEloRanking()) &&
                Objects.equals(getGamesPlayed(), user.getGamesPlayed()) &&
                Objects.equals(getGamesWinPercent(), user.getGamesWinPercent()) &&
                Objects.equals(getOppsWinPercent(), user.getOppsWinPercent()) &&
                Objects.equals(getDecks(), user.getDecks());
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", eloRanking=" + eloRanking +
                ", gamesPlayed=" + gamesPlayed +
                ", gamesWinPercent=" + gamesWinPercent +
                ", oppsWinPercent=" + oppsWinPercent +
                '}';
    }
}
