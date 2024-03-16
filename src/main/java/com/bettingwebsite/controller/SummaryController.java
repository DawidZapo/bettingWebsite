package com.bettingwebsite.controller;

import com.bettingwebsite.entity.Match;
import com.bettingwebsite.entity.User;
import com.bettingwebsite.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SummaryController {

    private MatchService matchService;

    @Autowired
    public SummaryController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/summary")
    public String showSummary(Model model){
        User user = LoginController.getLogonUserAndSetAttributes(model);

        model.addAttribute("matchForHTML", new Match());

        List<String> rounds = matchService.findDistinctByRound();
        model.addAttribute("rounds", rounds);

        List<Match> matchesWithBets = matchService.findAllByBetsIsNotEmpty();
        model.addAttribute("matchesWithBets",matchesWithBets);


        return "summary";
    }
}
