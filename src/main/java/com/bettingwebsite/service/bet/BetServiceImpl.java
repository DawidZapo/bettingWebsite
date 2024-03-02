package com.bettingwebsite.service.bet;

import com.bettingwebsite.dao.BetRepository;
import com.bettingwebsite.entity.Bet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetServiceImpl implements BetService {
    private BetRepository betRepository;

    @Autowired
    public BetServiceImpl(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @Override
    public Bet findBetByUserIdAndMatchToBetIdAndBetOnPlayerId(Long userId, Long matchToBetId, Long betOnPlayerId) {
        return betRepository.findBetByUserIdAndMatchToBetIdAndBetOnPlayerId(userId,matchToBetId,betOnPlayerId);
    }
}
