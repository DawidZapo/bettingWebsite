package com.bettingwebsite.service.match;

import com.bettingwebsite.dao.MatchRepository;
import com.bettingwebsite.entity.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {
    private MatchRepository matchRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public List<String> findDistinctByRound() {
        return matchRepository.findDistinctByRound();
    }

    @Override
    public List<Match> findAllByRound(String round) {
        return matchRepository.findAllByRoundOrderByMatchDateAscMatchTimeAsc(round);
    }

    @Override
    public List<Match> findMatchesByRound(String round) {
        return matchRepository.findMatchesByRound(round);
    }

    @Override
    public Match findById(Long id) {
        Optional<Match> result = matchRepository.findById(id);
        Match match = null;

        if(result.isPresent()){
            match = result.get();
        }
        else{
            throw new RuntimeException("Did not found match id: " + id);
        }
        return match;
    }

    @Override
    public Boolean checkIfRoundIsEligibleForBetting(String round) {
        Map<String,Integer> roundsMap = Match.getRoundsMap();

        int startingRound = roundsMap.getOrDefault(round, -1);

        if (startingRound == -1) {
            throw new RuntimeException("Round does not exists in Match.getRoundsMap()");
        }

        for (int i = startingRound -1; i >= 1; i--) {
            String roundToCheck = getKeyByValue(roundsMap,i);
            if(roundToCheck == null){
                throw new RuntimeException("Round to check not found");
            }
            List<Match> matches = matchRepository.findAllByRoundAndScoreIsNullAndWinnerIsNull(roundToCheck);
            if(matches.size() != 0){
                return false;
            }
        }

        return true;
    }
    public  <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    @Override
    public Match save(Match match) {
        return matchRepository.save(match);
    }
}
