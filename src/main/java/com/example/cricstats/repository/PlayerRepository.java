package com.example.cricstats.repository;
import com.example.cricstats.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
