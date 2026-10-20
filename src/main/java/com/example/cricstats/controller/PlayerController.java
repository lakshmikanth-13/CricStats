package com.example.cricstats.controller;
import com.example.cricstats.exception.ResourceNotFoundException;
import com.example.cricstats.model.Player;
import com.example.cricstats.model.Team;
import com.example.cricstats.repository.PlayerRepository;
import com.example.cricstats.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerController(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found with id " + id));
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@Valid @RequestBody Player player) {
        Team team = findTeam(player);
        if (team == null) {
            return ResponseEntity.badRequest().build();
        }
        player.setTeam(team);
        Player saved = playerRepository.save(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id,@Valid @RequestBody Player updated) {
        Team team = findTeam(updated);
        if (team == null) {
            return ResponseEntity.badRequest().build();
        }
        return playerRepository.findById(id).map(player -> {
            player.setName(updated.getName());
            player.setRole(updated.getRole());
            player.setTeam(team);
            return ResponseEntity.ok(playerRepository.save(player));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        if (!playerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        playerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Looks up the real Team from the id sent in the request
    private Team findTeam(Player player) {
        if (player.getTeam() == null || player.getTeam().getId() == null) {
            return null;
        }
        return teamRepository.findById(player.getTeam().getId()).orElse(null);
    }
}
