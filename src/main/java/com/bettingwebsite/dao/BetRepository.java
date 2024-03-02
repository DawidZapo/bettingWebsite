package com.bettingwebsite.dao;

import com.bettingwebsite.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BetRepository extends JpaRepository<Bet, Long> {
    @Query("SELECT b FROM Bet b WHERE b.user.id = :userId AND b.match.id = :matchToBetId AND b.betOnPlayer.id = :betOnPlayerId")
    Bet findBetByUserIdAndMatchToBetIdAndBetOnPlayerId(
            @Param("userId") Long userId,
            @Param("matchToBetId") Long matchToBetId,
            @Param("betOnPlayerId") Long betOnPlayerId
    );
}
