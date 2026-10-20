package com.example.cricstats.repository;
import com.example.cricstats.model.BattingPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BattingPerformanceRepository extends JpaRepository<BattingPerformance, Long> {
    List<BattingPerformance> findByPlayerId(Long playerId);
}
