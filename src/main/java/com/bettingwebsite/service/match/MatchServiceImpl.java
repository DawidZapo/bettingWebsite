package com.bettingwebsite.service.match;

import com.bettingwebsite.dao.MatchRepository;
import com.bettingwebsite.entity.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return matchRepository.findAllByRound(round);
    }

    @Override
    public List<Match> findMatchesByRound(String round) {
        return matchRepository.findMatchesByRound(round);
    }
}
