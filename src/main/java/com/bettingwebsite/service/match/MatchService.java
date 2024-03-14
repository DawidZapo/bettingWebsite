package com.bettingwebsite.service.match;

import com.bettingwebsite.entity.Match;

import java.util.List;

public interface MatchService {
    List<String> findDistinctByRound();
    List<Match> findAllByRoundOrderByDateAndTime(String round);
    List<Match> findAllByRoundAndScoreIsNullAndWinnerIsNull(String round);
    List<Match> findMatchesByRound(String round);
    Match findById(Long id);
    Boolean checkIfRoundIsEligibleForBetting(String round);
    List<Match> findAll();
    List<Match> findAllByBetsIsNotEmpty();
    Match save(Match match);
}