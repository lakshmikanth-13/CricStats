package com.example.cricstats.dto;

public record TeamStanding(String team, String shortName, int played, int won, int lost,
                           int noResult, int points, double netRunRate) {
}
