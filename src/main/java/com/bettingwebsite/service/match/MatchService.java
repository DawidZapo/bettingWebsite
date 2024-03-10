package com.bettingwebsite.service.match;

import com.bettingwebsite.entity.Match;

import java.util.List;

public interface MatchService {
    List<String> findDistinctByRound();
    List<Match> findAllByRound(String round);
    List<Match> findMatchesByRound(String round);
    Match findById(Long id);
    Boolean checkIfRoundIsEligibleForBetting(String round);
}