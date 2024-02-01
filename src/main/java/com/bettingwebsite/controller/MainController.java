package com.bettingwebsite.controller;

import com.bettingwebsite.entity.Match;
import com.bettingwebsite.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {
    private MatchService matchService;

    @Autowired
    public MainController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/")
    public String showMatches(Model model, @RequestParam(name = "round", required = false)String round){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("username", username);
        }

        List<String> rounds = matchService.findDistinctByRound();
        model.addAttribute("rounds", rounds);
        if(round == null){
            round = rounds.get(rounds.size()-1);
        }
        model.addAttribute("round", round);

        model.addAttribute("matchForHTML", new Match());

        List<Match> matches = matchService.findMatchesByRound(round);
        model.addAttribute("matches",matches);

        return "matches";
    }
}
