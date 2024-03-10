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
public class SystemController {
    private MatchService matchService;

    @Autowired
    public SystemController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/systems")
    public String showSystems(Model model){
        User user = LoginController.getLogonUserAndSetAttributes(model);

        List<Match> matches = matchService.findAll();
        model.addAttribute("matches",matches);

        List<String> rounds = matchService.findDistinctByRound();
        model.addAttribute("rounds", rounds);

        model.addAttribute("matchForHTML", new Match());

        return "systems";
    }
}
