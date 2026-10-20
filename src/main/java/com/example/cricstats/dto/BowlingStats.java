package com.example.cricstats.dto;

public record BowlingStats(Long playerId, String playerName, String team,
                           int innings, String overs, int balls, int runsConceded, int wickets,
                           Double average, double economy, Double strikeRate) {
}
