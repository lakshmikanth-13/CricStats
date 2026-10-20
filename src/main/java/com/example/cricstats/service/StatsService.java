package com.example.cricstats.service;

import com.example.cricstats.dto.*;
import com.example.cricstats.exception.ResourceNotFoundException;
import com.example.cricstats.exception.UndefinedStatException;
import com.example.cricstats.model.*;
import com.example.cricstats.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatsService {

    // ===== Settings you can change =====
    // MVP points formula
    private static final int POINTS_PER_RUN = 1;
    private static final int POINTS_PER_FOUR = 1;    // bonus on top of the run points
    private static final int POINTS_PER_SIX = 2;     // bonus on top of the run points
    private static final int BONUS_FIFTY = 10;
    private static final int BONUS_HUNDRED = 25;
    private static final int POINTS_PER_WICKET = 25;

    // Minimum balls to qualify for the "rate" leaderboards
    private static final int MIN_BALLS_BATTING = 30;
    private static final int MIN_BALLS_BOWLING = 24;   // 4 overs

    private static final int FULL_INNINGS_BALLS = 120;  // 20 overs

    private final BattingPerformanceRepository battingRepository;
    private final BowlingPerformanceRepository bowlingRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final IplMatchRepository matchRepository;

    public StatsService(BattingPerformanceRepository battingRepository,
                        BowlingPerformanceRepository bowlingRepository,
                        PlayerRepository playerRepository,
                        TeamRepository teamRepository,
                        IplMatchRepository matchRepository) {
        this.battingRepository = battingRepository;
        this.bowlingRepository = bowlingRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    // ================= BATTING =================

    public List<BattingStats> getBattingStats() {
        // group all innings rows by player id
        Map<Long, List<BattingPerformance>> byPlayer = battingRepository.findAll().stream()
                .collect(Collectors.groupingBy(b -> b.getPlayer().getId()));

        List<BattingStats> result = new ArrayList<>();
        for (List<BattingPerformance> innings : byPlayer.values()) {
            Player player = innings.get(0).getPlayer();
            int runs = 0, balls = 0, fours = 0, sixes = 0;
            int fifties = 0, hundreds = 0, highest = 0, dismissals = 0;

            for (BattingPerformance b : innings) {
                runs += b.getRuns();
                balls += b.getBallsFaced();
                fours += b.getFours();
                sixes += b.getSixes();
                if (b.getRuns() >= 100) {
                    hundreds++;
                } else if (b.getRuns() >= 50) {
                    fifties++;
                }
                highest = Math.max(highest, b.getRuns());
                if (!b.isNotOut()) {
                    dismissals++;
                }
            }
            result.add(new BattingStats(player.getId(), player.getName(),
                    player.getTeam().getShortName(), innings.size(), runs, balls,
                    fours, sixes, fifties, hundreds, highest, dismissals,
                    battingAverage(runs, dismissals), strikeRate(runs, balls)));
        }
        return result;
    }

    public List<BattingStats> getOrangeCap(int limit) {
        return topBatters(Comparator.comparingInt(BattingStats::runs)
                .thenComparingDouble(BattingStats::strikeRate).reversed(), limit);
    }

    public List<BattingStats> getMostSixes(int limit) {
        return topBatters(Comparator.comparingInt(BattingStats::sixes).reversed(), limit);
    }

    public List<BattingStats> getMostFours(int limit) {
        return topBatters(Comparator.comparingInt(BattingStats::fours).reversed(), limit);
    }

    public List<BattingStats> getMostFifties(int limit) {
        return topBatters(Comparator.comparingInt(BattingStats::fifties)
                .thenComparingInt(BattingStats::runs).reversed(), limit);
    }

    public List<BattingStats> getMostHundreds(int limit) {
        return topBatters(Comparator.comparingInt(BattingStats::hundreds)
                .thenComparingInt(BattingStats::runs).reversed(), limit);
    }

    public List<BattingStats> getBestBattingAverage(int limit) {
        return getBattingStats().stream()
                .filter(b -> b.average() != null)           // skip players never dismissed
                .sorted(Comparator.comparingDouble(BattingStats::average).reversed())
                .limit(limit).toList();
    }

    public List<BattingStats> getBestBattingStrikeRate(int limit) {
        return getBattingStats().stream()
                .filter(b -> b.balls() >= MIN_BALLS_BATTING)
                .sorted(Comparator.comparingDouble(BattingStats::strikeRate).reversed())
                .limit(limit).toList();
    }

    private List<BattingStats> topBatters(Comparator<BattingStats> order, int limit) {
        return getBattingStats().stream().sorted(order).limit(limit).toList();
    }

    // ================= BOWLING =================

    public List<BowlingStats> getBowlingStats() {
        Map<Long, List<BowlingPerformance>> byPlayer = bowlingRepository.findAll().stream()
                .collect(Collectors.groupingBy(b -> b.getPlayer().getId()));

        List<BowlingStats> result = new ArrayList<>();
        for (List<BowlingPerformance> innings : byPlayer.values()) {
            Player player = innings.get(0).getPlayer();
            int balls = 0, runs = 0, wickets = 0;

            for (BowlingPerformance b : innings) {
                balls += b.getBallsBowled();
                runs += b.getRunsConceded();
                wickets += b.getWickets();
            }
            result.add(new BowlingStats(player.getId(), player.getName(),
                    player.getTeam().getShortName(), innings.size(), toOvers(balls), balls,
                    runs, wickets, bowlingAverage(runs, wickets),
                    economy(runs, balls), bowlingStrikeRate(balls, wickets)));
        }
        return result;
    }

    public List<BowlingStats> getPurpleCap(int limit) {
        return getBowlingStats().stream()
                .sorted(Comparator.comparingInt(BowlingStats::wickets).reversed()
                        .thenComparingDouble(BowlingStats::economy))
                .limit(limit).toList();
    }

    public List<BowlingStats> getBestBowlingAverage(int limit) {
        return getBowlingStats().stream()
                .filter(b -> b.wickets() > 0 && b.balls() >= MIN_BALLS_BOWLING)
                .sorted(Comparator.comparingDouble(BowlingStats::average))   // lower is better
                .limit(limit).toList();
    }

    public List<BowlingStats> getBestBowlingStrikeRate(int limit) {
        return getBowlingStats().stream()
                .filter(b -> b.wickets() > 0 && b.balls() >= MIN_BALLS_BOWLING)
                .sorted(Comparator.comparingDouble(BowlingStats::strikeRate))  // lower is better
                .limit(limit).toList();
    }

    // ================= MVP =================

    public List<MvpEntry> getMvp(int limit) {
        Map<Long, Integer> battingPoints = new HashMap<>();
        for (BattingStats b : getBattingStats()) {
            int points = b.runs() * POINTS_PER_RUN
                    + b.fours() * POINTS_PER_FOUR
                    + b.sixes() * POINTS_PER_SIX
                    + b.fifties() * BONUS_FIFTY
                    + b.hundreds() * BONUS_HUNDRED;
            battingPoints.put(b.playerId(), points);
        }
        Map<Long, Integer> bowlingPoints = new HashMap<>();
        for (BowlingStats b : getBowlingStats()) {
            bowlingPoints.put(b.playerId(), b.wickets() * POINTS_PER_WICKET);
        }

        List<MvpEntry> result = new ArrayList<>();
        for (Player p : playerRepository.findAll()) {
            int bat = battingPoints.getOrDefault(p.getId(), 0);
            int bowl = bowlingPoints.getOrDefault(p.getId(), 0);
            if (bat + bowl > 0) {
                result.add(new MvpEntry(p.getId(), p.getName(), p.getTeam().getShortName(),
                        bat, bowl, bat + bowl));
            }
        }
        return result.stream()
                .sorted(Comparator.comparingInt(MvpEntry::totalPoints).reversed())
                .limit(limit).toList();
    }

    // ================= POINTS TABLE =================

    public List<TeamStanding> getPointsTable() {
        Map<Long, StandingRow> rows = new LinkedHashMap<>();
        for (Team t : teamRepository.findAll()) {
            rows.put(t.getId(), new StandingRow(t));
        }

        for (IplMatch m : matchRepository.findAll()) {
            StandingRow r1 = rows.get(m.getTeam1().getId());
            StandingRow r2 = rows.get(m.getTeam2().getId());
            r1.played++;
            r2.played++;

            if (m.getWinner() == null) {            // no result: 1 point each
                r1.noResult++;
                r2.noResult++;
                r1.points++;
                r2.points++;
                continue;                           // no result matches don't count for NRR
            }

            boolean team1Won = m.getWinner().getId().equals(m.getTeam1().getId());
            StandingRow winner = team1Won ? r1 : r2;
            StandingRow loser = team1Won ? r2 : r1;
            winner.won++;
            winner.points += 2;
            loser.lost++;

            // net run rate figures (a team that is all out is counted as facing 20 overs)
            int balls1 = effectiveBalls(m.getTeam1Wickets(), m.getTeam1Balls());
            int balls2 = effectiveBalls(m.getTeam2Wickets(), m.getTeam2Balls());
            r1.runsFor += m.getTeam1Runs();
            r1.ballsFor += balls1;
            r1.runsAgainst += m.getTeam2Runs();
            r1.ballsAgainst += balls2;
            r2.runsFor += m.getTeam2Runs();
            r2.ballsFor += balls2;
            r2.runsAgainst += m.getTeam1Runs();
            r2.ballsAgainst += balls1;
        }

        return rows.values().stream()
                .map(r -> new TeamStanding(r.team.getName(), r.team.getShortName(),
                        r.played, r.won, r.lost, r.noResult, r.points, r.netRunRate()))
                .sorted(Comparator.comparingInt(TeamStanding::points)
                        .thenComparingDouble(TeamStanding::netRunRate).reversed())
                .toList();
    }

    private int effectiveBalls(int wickets, int balls) {
        return wickets >= 10 ? FULL_INNINGS_BALLS : balls;
    }

    // holds one team's running totals while we go through the matches
    private static class StandingRow {
        final Team team;
        int played, won, lost, noResult, points;
        int runsFor, ballsFor, runsAgainst, ballsAgainst;

        StandingRow(Team team) {
            this.team = team;
        }

        double netRunRate() {
            if (ballsFor == 0 || ballsAgainst == 0) {
                return 0;
            }
            double rate = (runsFor * 6.0 / ballsFor) - (runsAgainst * 6.0 / ballsAgainst);
            return Math.round(rate * 1000.0) / 1000.0;
        }
    }

    // ================= FORMULAS =================

    // Batting average = runs / dismissals. Never dismissed -> undefined (null).
    private Double battingAverage(int runs, int dismissals) {
        if (dismissals == 0) {
            return null;
        }
        return round(runs / (double) dismissals);
    }

    // Batting strike rate = runs per 100 balls
    private double strikeRate(int runs, int balls) {
        if (balls == 0) {
            return 0;
        }
        return round(runs * 100.0 / balls);
    }

    // Bowling average = runs conceded / wickets. No wickets -> undefined (null).
    private Double bowlingAverage(int runsConceded, int wickets) {
        if (wickets == 0) {
            return null;
        }
        return round(runsConceded / (double) wickets);
    }

    // Economy = runs conceded per over
    private double economy(int runsConceded, int balls) {
        if (balls == 0) {
            return 0;
        }
        return round(runsConceded * 6.0 / balls);
    }

    // Bowling strike rate = balls per wicket. No wickets -> undefined (null).
    private Double bowlingStrikeRate(int balls, int wickets) {
        if (wickets == 0) {
            return null;
        }
        return round(balls / (double) wickets);
    }

    // 22 balls -> "3.4"
    private String toOvers(int balls) {
        return (balls / 6) + "." + (balls % 6);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    public BattingStats getPlayerBatting(Long playerId) {
        return getBattingStats().stream()
                .filter(b -> b.playerId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No batting record for player " + playerId));
    }

    public double getPlayerBattingAverage(Long playerId) {
        BattingStats stats = getPlayerBatting(playerId);
        if (stats.average() == null) {
            throw new UndefinedStatException(stats.playerName() + " has " + stats.runs()
                    + " runs but has never been dismissed, so the batting average is undefined");
        }
        return stats.average();
    }
}
