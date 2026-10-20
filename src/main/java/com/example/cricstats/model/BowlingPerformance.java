package com.example.cricstats.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class BowlingPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private IplMatch match;

    @Min(0) @Max(value = 24, message = "A bowler can bowl at most 24 balls (4 overs) in T20")
    private int ballsBowled;
    @Min(0) private int runsConceded;
    @Min(0) @Max(value = 10, message = "Wickets cannot be more than 10")
    private int wickets;

    public BowlingPerformance() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }

    public IplMatch getMatch() { return match; }
    public void setMatch(IplMatch match) { this.match = match; }

    public int getBallsBowled() { return ballsBowled; }
    public void setBallsBowled(int ballsBowled) { this.ballsBowled = ballsBowled; }

    public int getRunsConceded() { return runsConceded; }
    public void setRunsConceded(int runsConceded) { this.runsConceded = runsConceded; }

    public int getWickets() { return wickets; }
    public void setWickets(int wickets) { this.wickets = wickets; }
}
