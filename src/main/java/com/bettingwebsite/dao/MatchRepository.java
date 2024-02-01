package com.bettingwebsite.dao;

import com.bettingwebsite.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("SELECT DISTINCT m.round FROM Match m")
    List<String> findDistinctByRound();

    List<Match> findAllByRound(String round);

    @Query("SELECT m FROM Match m " +
            "JOIN m.player1 p1 " +
            "JOIN m.player2 p2 " +
            "WHERE m.round = :round " +
            "ORDER BY m.matchDate, m.matchTime")
    List<Match> findMatchesByRound(String round);

}
