package com.bettingwebsite.controller;

import com.bettingwebsite.entity.Bet;
import com.bettingwebsite.entity.Match;
import com.bettingwebsite.entity.Player;
import com.bettingwebsite.entity.User;
import com.bettingwebsite.service.UserService;
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
    private UserService userService;

    @Autowired
    public MainController(MatchService matchService, UserService userService) {
        this.matchService = matchService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String showMatches(Model model, @RequestParam(name = "round", required = false)String round){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("username", username);

            user = userService.findByUserName(username);
            model.addAttribute("userDetails", user.getUserDetails());
        }

        List<String> rounds = matchService.findDistinctByRound();
        model.addAttribute("rounds", rounds);
        if(round == null){
            round = rounds.get(rounds.size()-1);
        }
        model.addAttribute("round", round);

        model.addAttribute("matchForHTML", new Match());

        List<Match> matches = matchService.findMatchesByRound(round);
        List<Match> filteredMatchesByUser = Match.filterMatchesByUser(matches,user);

        model.addAttribute("matches",filteredMatchesByUser);

        model.addAttribute("atpChecked", true);
        model.addAttribute("wtaChecked", true);



        return "matches";
    }

    @GetMapping("/processPointsSubmission")
    public String processPointsSubmission(@RequestParam("arguments")String arguments){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            user = userService.findByUserName(username);
        }


        String[] parts = arguments.split(";");
        for (String part : parts) {
            System.out.println(part);
            double betValue = Double.parseDouble(part.substring(0, part.indexOf("inputMatch")));

            Long matchId = Long.parseLong(part.substring(part.indexOf("inputMatch") + 10, part.indexOf("player")));

            String playerToBet = part.contains("player1") ? "player1" : "player2";

            System.out.println("Wartość zakładu: " + betValue);
            System.out.println("ID meczu: " + matchId);
            System.out.println("PlayerToBet: " + playerToBet);
            System.out.println("=============");

            Match match = matchService.findById(matchId);
            Match filteredMatch = Match.filterMatchByUser(match,user);

            Double expectedWin = (getOdds(playerToBet,match) * betValue);
            Bet bet = new Bet(betValue,getPlayerToBet(playerToBet,match),expectedWin);

            user.addBet(bet,match);
            user.getUserDetails().setPoints(user.getUserDetails().getPoints()-betValue);
            userService.save(user);
        }
        return "redirect:/";
    }

    private double getOdds(String player, Match match){
        if(player.equals("player1")){
            return match.getPlayer1Odds();
        }
        else{
            return match.getPlayer2Odds();
        }
    }
    private Player getPlayerToBet(String player, Match match){
        if(player.equals("player1")){
            return match.getPlayer1();
        }
        else{
            return match.getPlayer2();
        }
    }
}
