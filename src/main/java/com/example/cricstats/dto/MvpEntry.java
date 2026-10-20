package com.example.cricstats.dto;

public record MvpEntry(Long playerId, String playerName, String team,
                       int battingPoints, int bowlingPoints, int totalPoints) {
}