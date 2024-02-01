package com.bettingwebsite.rest;

import com.bettingwebsite.entity.Match;
import com.bettingwebsite.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    private MatchService matchService;

    @Autowired
    public RestController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/distinctRound")
    public List<String> getMatchDistinctRound(){
        return matchService.findDistinctByRound();
    }

    @GetMapping("/findMatchesByRound")
    public List<Match> findAllByRound(String round){
        return matchService.findMatchesByRound(round);
    }
}
