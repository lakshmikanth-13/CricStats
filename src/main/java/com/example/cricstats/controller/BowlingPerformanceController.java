package com.example.cricstats.controller;

import com.example.cricstats.model.BowlingPerformance;
import com.example.cricstats.model.IplMatch;
import com.example.cricstats.model.Player;
import com.example.cricstats.repository.BowlingPerformanceRepository;
import com.example.cricstats.repository.IplMatchRepository;
import com.example.cricstats.repository.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bowling")
public class BowlingPerformanceController {

    private final BowlingPerformanceRepository bowlingRepository;
    private final PlayerRepository playerRepository;
    private final IplMatchRepository matchRepository;

    public BowlingPerformanceController(BowlingPerformanceRepository bowlingRepository,
                                        PlayerRepository playerRepository,
                                        IplMatchRepository matchRepository) {
        this.bowlingRepository = bowlingRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping
    public List<BowlingPerformance> getAll() {
        return bowlingRepository.findAll();
    }

    @GetMapping("/player/{playerId}")
    public List<BowlingPerformance> getByPlayer(@PathVariable Long playerId) {
        return bowlingRepository.findByPlayerId(playerId);
    }

    @PostMapping
    public ResponseEntity<BowlingPerformance> create(@Valid @RequestBody BowlingPerformance performance) {
        if (performance.getPlayer() == null || performance.getPlayer().getId() == null
                || performance.getMatch() == null || performance.getMatch().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Player player = playerRepository.findById(performance.getPlayer().getId()).orElse(null);
        IplMatch match = matchRepository.findById(performance.getMatch().getId()).orElse(null);
        if (player == null || match == null) {
            return ResponseEntity.badRequest().build();
        }
        performance.setPlayer(player);
        performance.setMatch(match);
        return ResponseEntity.status(HttpStatus.CREATED).body(bowlingRepository.save(performance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!bowlingRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bowlingRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
