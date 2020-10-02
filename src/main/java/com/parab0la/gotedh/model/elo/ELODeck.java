package com.parab0la.gotedh.model.elo;

public class ELODeck {

    private String name;
    private Integer place;
    private Integer eloPre;
    private Integer eloPost;
    private Integer eloChange;
    private Integer gamesPlayed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Integer getEloPre() {
        return eloPre;
    }

    public void setEloPre(Integer eloPre) {
        this.eloPre = eloPre;
    }

    public Integer getEloPost() {
        return eloPost;
    }

    public void setEloPost(Integer eloPost) {
        this.eloPost = eloPost;
    }

    public Integer getEloChange() {
        return eloChange != null ? eloChange : 0;
    }

    public void setEloChange(Integer eloChange) {
        this.eloChange = eloChange;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
}
