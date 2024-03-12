package com.bettingwebsite.service.bet;

import com.bettingwebsite.dao.BetRepository;
import com.bettingwebsite.dao.UserDao;
import com.bettingwebsite.entity.Bet;
import com.bettingwebsite.entity.Match;
import com.bettingwebsite.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BetServiceImpl implements BetService {
    private BetRepository betRepository;
    private UserDao userDao;

    @Autowired
    public BetServiceImpl(BetRepository betRepository, UserDao userDao) {
        this.betRepository = betRepository;
        this.userDao = userDao;
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

    @Override
    @Transactional
    public void redeemBets(Match match){
        if(match.getWinner() != null){

            for(var bet : match.getBets()){
                User user = userDao.findById(bet.getUser().getId());

                double currentPoints = user.getUserDetails().getPoints();
                user.getUserDetails().setPoints(currentPoints - bet.getAmount());

                if (bet.getRedeemed() && !bet.getSucceed()) {
                    user.getUserDetails().setPoints(currentPoints + bet.getAmount());
                }

                bet.setRedeemed(true);

                if(bet.getBetOnPlayer().getId().equals(match.getWinner().getId())){
                    bet.setSucceed(true);
                    user.getUserDetails().setPoints(user.getUserDetails().getPoints() + bet.getExpectedWin());
                }
                else{
                    bet.setSucceed(false);
                }

                userDao.save(user);
                betRepository.save(bet);
            }
        }
    }
}
