package com.example.cricstats.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "ipl_match")
public class IplMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Match number must be at least 1")
    private int matchNumber;

    @NotNull(message = "Match date is required")
    private LocalDate matchDate;
    private String venue;

    @NotNull(message = "Team 1 is required")
    @ManyToOne
    @JoinColumn(name = "team1_id")
    private Team team1;

    @NotNull(message = "Team 2 is required")
    @ManyToOne
    @JoinColumn(name = "team2_id")
    private Team team2;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Team winner;   // stays null if the match has no result

    @ManyToOne
    @JoinColumn(name = "man_of_match_id")
    private Player manOfTheMatch;

    @Min(0) private int team1Runs;
    @Min(0) @Max(10) private int team1Wickets;
    @Min(0) @Max(120) private int team1Balls;
    @Min(0) private int team2Runs;
    @Min(0) @Max(10) private int team2Wickets;
    @Min(0) @Max(120) private int team2Balls;
    private String resultText;

    public IplMatch() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMatchNumber() {
        return matchNumber;
    }
    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }
    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public String getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }
    public Team getTeam1() {
        return team1;
    }
    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }
    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Team getWinner() {
        return winner;
    }
    public void setWinner(Team winner) {
        this.winner = winner;
    }

    public int getTeam1Balls() {
        return team1Balls;
    }

    public int getTeam1Runs() {
        return team1Runs;
    }

    public int getTeam1Wickets() {
        return team1Wickets;
    }

    public int getTeam2Balls() {
        return team2Balls;
    }

    public int getTeam2Runs() {
        return team2Runs;
    }

    public int getTeam2Wickets() {
        return team2Wickets;
    }

    public Player getManOfTheMatch() {
        return manOfTheMatch;
    }

    public String getResultText() {
        return resultText;
    }

    public void setManOfTheMatch(Player manOfTheMatch) {
        this.manOfTheMatch = manOfTheMatch;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public void setTeam1Balls(int team1Balls) {
        this.team1Balls = team1Balls;
    }

    public void setTeam1Runs(int team1Runs) {
        this.team1Runs = team1Runs;
    }

    public void setTeam1Wickets(int team1Wickets) {
        this.team1Wickets = team1Wickets;
    }

    public void setTeam2Balls(int team2Balls) {
        this.team2Balls = team2Balls;
    }

    public void setTeam2Runs(int team2Runs) {
        this.team2Runs = team2Runs;
    }

    public void setTeam2Wickets(int team2Wickets) {
        this.team2Wickets = team2Wickets;
    }
}
