package com.example.cricstats.dto;

public record BattingStats(Long playerId, String playerName, String team,
                           int innings, int runs, int balls, int fours, int sixes,
                           int fifties, int hundreds, int highestScore, int dismissals,
                           Double average, double strikeRate) {
}
