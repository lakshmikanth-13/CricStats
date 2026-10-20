package com.example.cricstats.controller;

import com.example.cricstats.dto.*;
import com.example.cricstats.service.StatsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/batting")
    public List<BattingStats> batting() { return statsService.getBattingStats(); }

    @GetMapping("/bowling")
    public List<BowlingStats> bowling() { return statsService.getBowlingStats(); }

    @GetMapping("/orange-cap")
    public List<BattingStats> orangeCap(@RequestParam(defaultValue = "10") int limit) {
        return statsService.getOrangeCap(limit);
    }

    @GetMapping("/purple-cap")
    public List<BowlingStats> purpleCap(@RequestParam(defaultValue = "10") int limit) {
        return statsService.getPurpleCap(limit);
    }

    @GetMapping("/most-sixes")
    public List<BattingStats> mostSixes(@RequestParam(defaultValue = "10") int limit) {
        return statsService.getMostSixes(limit);
    }

    @GetMapping("/most-fours")
    public List<BattingStats> mostFours(@RequestParam(defaultValue = "10") int limit) {
        return statsService.getMostFours(limit);
    }

    @GetMapping("/most-fifties")
    public List<BattingStats> mostFifties(@RequestParam(defaultValue = "10") int limit) {
        return statsService.getMostFifties(limit);
    }

    @GetMapping("/most-hundreds")
    public List<BattingStats> mostHundreds(@RequestParam(defaultValue = "10") int limit) {
        return statsService.getMostHundreds(limit);
    }

    @GetMapping("/batting-average")
    public List<BattingStats> battingAverage(@RequestParam(defaultValue = "10") int limit) {
        return statsService.getBestBattingAverage(limit);
    }

    @GetMapping("/batting-strike-rate")
    public List<BattingStats> battingStrikeRate(@RequestParam(defaultValue = "10") int limit) {
        return statsService.getBestBattingStrikeRate(limit);
    }

    @GetMapping("/bowling-average")
    public List<BowlingStats> bowlingAverage(@RequestParam(defaultValue = "10") int limit) {
        return statsService.getBestBowlingAverage(limit);
    }

    @GetMapping("/bowling-strike-rate")
    public List<BowlingStats> bowlingStrikeRate(@RequestParam(defaultValue = "10") int limit) {
        return statsService.getBestBowlingStrikeRate(limit);
    }

    @GetMapping("/mvp")
    public List<MvpEntry> mvp(@RequestParam(defaultValue = "10") int limit) {
        return statsService.getMvp(limit);
    }

    @GetMapping("/points-table")
    public List<TeamStanding> pointsTable() {
        return statsService.getPointsTable();
    }
    @GetMapping("/players/{id}/batting")
    public BattingStats playerBatting(@PathVariable Long id) {
        return statsService.getPlayerBatting(id);
    }

    @GetMapping("/players/{id}/batting-average")
    public Map<String, Object> playerBattingAverage(@PathVariable Long id) {
        BattingStats stats = statsService.getPlayerBatting(id);
        return Map.of("player", stats.playerName(),
                "average", statsService.getPlayerBattingAverage(id));
    }
}
