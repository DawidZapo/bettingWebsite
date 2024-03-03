package com.bettingwebsite.service.bet;

import com.bettingwebsite.entity.Bet;

import java.util.List;

public interface BetService {
    Bet findBetByUserIdAndMatchToBetIdAndBetOnPlayerId(Long userId, Long matchToBetId, Long betOnPlayerId);
    List<Bet> findByUserId(Long userId);
}
