package com.example.cricstats.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;

@Entity
public class BattingPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private IplMatch match;

    @Min(value = 0, message = "Runs cannot be negative") private int runs;
    @Min(value = 0, message = "Balls faced cannot be negative") private int ballsFaced;
    @Min(value = 0, message = "Fours cannot be negative") private int fours;
    @Min(value = 0, message = "Sixes cannot be negative") private int sixes;
    private boolean notOut;   // true if batter out flase if batter dismissed

    public BattingPerformance() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }

    public IplMatch getMatch() { return match; }
    public void setMatch(IplMatch match) { this.match = match; }

    public int getRuns() { return runs; }
    public void setRuns(int runs) { this.runs = runs; }

    public int getBallsFaced() { return ballsFaced; }
    public void setBallsFaced(int ballsFaced) { this.ballsFaced = ballsFaced; }

    public int getFours() { return fours; }
    public void setFours(int fours) { this.fours = fours; }

    public int getSixes() { return sixes; }
    public void setSixes(int sixes) { this.sixes = sixes; }

    public boolean isNotOut() { return notOut; }
    public void setNotOut(boolean notOut) { this.notOut = notOut; }
}
