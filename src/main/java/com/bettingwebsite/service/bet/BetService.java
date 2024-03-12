package com.bettingwebsite.service.bet;

import com.bettingwebsite.entity.Bet;
import com.bettingwebsite.entity.Match;

import java.util.List;

public interface BetService {
    Bet findBetByUserIdAndMatchToBetIdAndBetOnPlayerId(Long userId, Long matchToBetId, Long betOnPlayerId);
    List<Bet> findByUserId(Long userId);
    Bet findById(Long id);
    void deleteById(Long id);
    void redeemBets(Match match);
}
