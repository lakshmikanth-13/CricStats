package com.example.cricstats.repository;
import com.example.cricstats.model.BowlingPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BowlingPerformanceRepository extends JpaRepository<BowlingPerformance, Long> {
    List<BowlingPerformance> findByPlayerId(Long playerId);
}
