package com.example.cricstats.controller;

import com.example.cricstats.model.IplMatch;
import com.example.cricstats.model.Team;
import com.example.cricstats.repository.IplMatchRepository;
import com.example.cricstats.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class IplMatchController {

    private final IplMatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public IplMatchController(IplMatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public List<IplMatch> getAllMatches() {
        return matchRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IplMatch> getMatch(@PathVariable Long id) {
        return matchRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<IplMatch> createMatch(@Valid @RequestBody IplMatch match) {
        if (!resolveTeams(match)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(matchRepository.save(match));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IplMatch> updateMatch(@PathVariable Long id,@Valid  @RequestBody IplMatch updated) {
        if (!resolveTeams(updated)) {
            return ResponseEntity.badRequest().build();
        }
        return matchRepository.findById(id).map(match -> {
            match.setMatchNumber(updated.getMatchNumber());
            match.setMatchDate(updated.getMatchDate());
            match.setVenue(updated.getVenue());
            match.setTeam1(updated.getTeam1());
            match.setTeam2(updated.getTeam2());
            match.setWinner(updated.getWinner());
            return ResponseEntity.ok(matchRepository.save(match));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        if (!matchRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        matchRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Replaces the team ids in the request with real Team objects.
    // Returns false if the data is invalid.
    private boolean resolveTeams(IplMatch match) {
        Team team1 = lookup(match.getTeam1());
        Team team2 = lookup(match.getTeam2());
        if (team1 == null || team2 == null || team1.getId().equals(team2.getId())) {
            return false;
        }
        Team winner = null;
        if (match.getWinner() != null) {
            winner = lookup(match.getWinner());
            boolean playing = winner != null
                    && (winner.getId().equals(team1.getId()) || winner.getId().equals(team2.getId()));
            if (!playing) {
                return false;
            }
        }
        match.setTeam1(team1);
        match.setTeam2(team2);
        match.setWinner(winner);
        return true;
    }

    private Team lookup(Team team) {
        if (team == null || team.getId() == null) {
            return null;
        }
        return teamRepository.findById(team.getId()).orElse(null);
    }
}
