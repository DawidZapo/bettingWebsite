package com.bettingwebsite.service.match;

import com.bettingwebsite.dao.MatchRepository;
import com.bettingwebsite.entity.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

}
