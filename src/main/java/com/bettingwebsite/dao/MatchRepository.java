package com.bettingwebsite.dao;

import com.bettingwebsite.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
