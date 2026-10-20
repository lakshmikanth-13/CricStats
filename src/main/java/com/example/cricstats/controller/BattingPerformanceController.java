package com.example.cricstats.controller;

import com.example.cricstats.exception.BadRequestException;
import com.example.cricstats.model.BattingPerformance;
import com.example.cricstats.model.IplMatch;
import com.example.cricstats.model.Player;
import com.example.cricstats.repository.BattingPerformanceRepository;
import com.example.cricstats.repository.IplMatchRepository;
import com.example.cricstats.repository.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/batting")
public class BattingPerformanceController {

    private final BattingPerformanceRepository battingRepository;
    private final PlayerRepository playerRepository;
    private final IplMatchRepository matchRepository;

    public BattingPerformanceController(BattingPerformanceRepository battingRepository,
                                        PlayerRepository playerRepository,
                                        IplMatchRepository matchRepository) {
        this.battingRepository = battingRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping
    public List<BattingPerformance> getAll() {
        return battingRepository.findAll();
    }

    @GetMapping("/player/{playerId}")
    public List<BattingPerformance> getByPlayer(@PathVariable Long playerId) {
        return battingRepository.findByPlayerId(playerId);
    }

    @PostMapping
    public ResponseEntity<BattingPerformance> create(@Valid @RequestBody BattingPerformance performance) {
        if (performance.getFours() * 4 + performance.getSixes() * 6 > performance.getRuns()) {
            throw new BadRequestException("Runs cannot be fewer than boundary runs (fours x 4 + sixes x 6)");
        }
        if (performance.getFours() + performance.getSixes() > performance.getBallsFaced()) {
            throw new BadRequestException("Fours plus sixes cannot be more than balls faced");
        }
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
        return ResponseEntity.status(HttpStatus.CREATED).body(battingRepository.save(performance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!battingRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        battingRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
