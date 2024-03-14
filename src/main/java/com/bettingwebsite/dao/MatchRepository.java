package com.bettingwebsite.dao;

import com.bettingwebsite.entity.Match;
import com.bettingwebsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("SELECT DISTINCT m.round FROM Match m")
    List<String> findDistinctByRound();
    List<Match> findAllByRoundOrderByMatchDateAscMatchTimeAsc(String round);
    List<Match> findAllByRoundAndScoreIsNullAndWinnerIsNull(String round);
    List<Match> findAllByRound(String round);
    List<Match> findAllByBetsIsNotEmpty();
    @Query("SELECT m FROM Match m " +
            "JOIN m.player1 p1 " +
            "JOIN m.player2 p2 " +
            "WHERE m.round = :round " +
            "ORDER BY m.matchDate, m.matchTime")
    List<Match> findMatchesByRound(String round);

    @Query("SELECT DISTINCT m FROM Match m JOIN FETCH m.bets b WHERE b.user = :user AND m.round =:round")
    List<Match> findMatchesByRoundAndUser(@Param("user") User user, @Param("round")String round);

}
