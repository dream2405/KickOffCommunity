package org.example.kickoffcommunity.board.entity;

public class TeamRanking {
    private String teamName;
    private int wins;
    private int draws;
    private int losses;
    private int points;

    public TeamRanking(String teamName) {
        this.teamName = teamName;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.points = 0;
    }

    public void addWin() {
        this.wins++;
        this.points += 3;
    }

    public void addDraw() {
        this.draws++;
        this.points += 1;
    }

    public void addLoss() {
        this.losses++;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }

    public int getPoints() {
        return points;
    }
}