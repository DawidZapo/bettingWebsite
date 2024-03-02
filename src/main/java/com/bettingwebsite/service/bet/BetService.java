package com.bettingwebsite.service.bet;

import com.bettingwebsite.entity.Bet;

public interface BetService {
    Bet findBetByUserIdAndMatchToBetIdAndBetOnPlayerId(Long userId, Long matchToBetId, Long betOnPlayerId);
}
