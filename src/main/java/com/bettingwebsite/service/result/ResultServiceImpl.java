package com.bettingwebsite.service.result;

import com.bettingwebsite.dao.ResultRepository;
import com.bettingwebsite.dao.UserDao;
import com.bettingwebsite.entity.Result;
import com.bettingwebsite.entity.User;
import com.bettingwebsite.entity.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class ResultServiceImpl implements ResultService{
    private ResultRepository resultRepository;
    private UserDao userDao;

    @Autowired
    public ResultServiceImpl(ResultRepository resultRepository, UserDao userDao) {
        this.resultRepository = resultRepository;
        this.userDao = userDao;
    }

    @Override
    public Result findById(Long id) {
        Optional<Result> result = resultRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        else{
            throw new RuntimeException("Did not found result id: " + id);
        }
    }

    @Override
    @Transactional
    public void updateAllUsersResults(String round) {
        List<User> users = userDao.findAll();
        users.forEach(user -> {
            Result result = user.getResult();
            UserDetails userDetails = user.getUserDetails();
            switch(round){
                case "round1" -> result.setAfterFirstRound(userDetails.getPoints());
                case "round2" -> result.setAfterSecondRound(userDetails.getPoints());
                case "round3" -> result.setAfterThirdRound(userDetails.getPoints());
                case "round4" -> result.setAfterFourthRound(userDetails.getPoints());
                case "quarter-final" -> result.setAfterQuarterFinal(userDetails.getPoints());
                case "semi-final" -> result.setAfterSemiFinal(userDetails.getPoints());
                case "final" -> result.setAfterFinal(userDetails.getPoints());
                default -> throw new RuntimeException("Round: " + round + " not found");
            }
            userDao.save(user);
        });
    }
}
