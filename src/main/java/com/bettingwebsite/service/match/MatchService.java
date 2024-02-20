package com.bettingwebsite.service.match;

import com.bettingwebsite.entity.Match;
import com.bettingwebsite.entity.User;

import java.util.List;

public interface MatchService {
    List<String> findDistinctByRound();
    List<Match> findAllByRound(String round);
    List<Match> findMatchesByRound(String round);
    Match findById(Long id);
    List<Match> findMatchesByRoundAndUser(String round, User user);
}