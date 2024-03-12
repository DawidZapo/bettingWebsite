package com.bettingwebsite.controller;

import com.bettingwebsite.entity.Match;
import com.bettingwebsite.entity.User;
import com.bettingwebsite.service.bet.BetService;
import com.bettingwebsite.service.match.MatchService;
import com.bettingwebsite.service.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SystemController {
    private MatchService matchService;
    private PlayerService playerService;
    private BetService betService;

    @Autowired
    public SystemController(MatchService matchService, PlayerService playerService, BetService betService) {
        this.matchService = matchService;
        this.playerService = playerService;
        this.betService = betService;
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

    @PostMapping("/systems/submitMatch")
    public String submitMatch(@RequestParam("id")Long matchId,@RequestParam(required = false, name = "selectedPlayer")Long playerId,@RequestParam("score")String score){
        if(playerId !=null){

            Match match = matchService.findById(matchId);
            if(playerId.equals(match.getPlayer1().getId())){
                match.setWinner(match.getPlayer1());
            }
            else if(playerId.equals(match.getPlayer2().getId())){
                match.setWinner(match.getPlayer2());
            }
            else{
                throw new RuntimeException("Player id: " + playerId + " not found in match id: " + matchId);
            }
            if(!score.isEmpty()){
                match.setScore(score);
            }
            matchService.save(match);
        }

        return "redirect:/systems";
    }
}
