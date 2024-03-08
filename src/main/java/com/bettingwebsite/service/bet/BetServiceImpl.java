package com.bettingwebsite.service.bet;

import com.bettingwebsite.dao.BetRepository;
import com.bettingwebsite.entity.Bet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Bet> findByUserId(Long userId) {
        return betRepository.findByUserId(userId);
    }

    @Override
    public Bet findById(Long id) {
        Optional<Bet> result = betRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        else{
            throw new RuntimeException("Bet not found id: " + id);
        }
    }

    @Override
    public void deleteById(Long id) {
        betRepository.deleteById(id);
    }
}
