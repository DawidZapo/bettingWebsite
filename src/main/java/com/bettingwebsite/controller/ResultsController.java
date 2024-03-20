package com.bettingwebsite.controller;

import com.bettingwebsite.entity.Match;
import com.bettingwebsite.entity.User;
import com.bettingwebsite.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ResultsController {
    private MatchService matchService;

    @Autowired
    public ResultsController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/results")
    public String showResults(@RequestParam(value = "round", required = false)String round, Model model){
        User user = LoginController.getLogonUserAndSetAttributes(model);
        List<String> rounds = matchService.findDistinctByRound();
        model.addAttribute("rounds", rounds);
        model.addAttribute("matchForHTML", new Match());

        return "results";
    }
}
