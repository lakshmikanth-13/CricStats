package com.example.cricstats.repository;
import com.example.cricstats.model.IplMatch;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IplMatchRepository extends JpaRepository<IplMatch, Long> {
}
