package com.bettingwebsite.controller;

import com.bettingwebsite.entity.Bet;
import com.bettingwebsite.entity.Match;
import com.bettingwebsite.entity.Player;
import com.bettingwebsite.entity.User;
import com.bettingwebsite.service.UserService;
import com.bettingwebsite.service.bet.BetService;
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
    private BetService betService;

    @Autowired
    public MainController(MatchService matchService, UserService userService, BetService betService) {
        this.matchService = matchService;
        this.userService = userService;
        this.betService = betService;
    }

    @GetMapping("/")
    public String showMatches(Model model, @RequestParam(name = "round", required = false)String round){
        User user = LoginController.getLogonUserAndSetAttributes(model);

        List<String> rounds = matchService.findDistinctByRound();
        model.addAttribute("rounds", rounds);
        if(round == null){
            if(!rounds.isEmpty()){
                round = rounds.get(rounds.size()-1);
            }
            else{
                round = "round1";
            }
        }
        model.addAttribute("round", round);

        model.addAttribute("matchForHTML", new Match());

        List<Match> matches = matchService.findMatchesByRound(round);
        List<Match> filteredMatchesByUser = Match.filterMatchesByUser(matches,user);

        model.addAttribute("matches",filteredMatchesByUser);

        model.addAttribute("atpChecked", true);
        model.addAttribute("wtaChecked", true);

        Boolean isRoundEligibleForBetting = matchService.checkIfRoundIsEligibleForBetting(round);
        model.addAttribute("isRoundEligibleForBetting",isRoundEligibleForBetting);

        return "matches";
    }

//    @GetMapping("/processPointsSubmission")
//    public String processPointsSubmission(@RequestParam("arguments")String arguments){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = null;
//        if (authentication != null && authentication.isAuthenticated()) {
//            String username = authentication.getName();
//            user = userService.findByUserName(username);
//        }
//
//        Match matchToEditUrl = null;
//        String[] parts = arguments.split(";");
//        for (String part : parts) {
//            double betValue = Double.parseDouble(part.substring(0, part.indexOf("inputMatch")));
//            Long matchId = Long.parseLong(part.substring(part.indexOf("inputMatch") + 10, part.indexOf("player")));
//            String playerToBet = part.contains("player1") ? "player1" : "player2";
//
//            Match match = matchService.findById(matchId);
//            Match filteredMatch = Match.filterMatchByUser(match,user);
//
//            Double expectedWin = (getOdds(playerToBet,match) * betValue);
//
//            String stringToFormat = String.format("%.5f", expectedWin);
//            stringToFormat = stringToFormat.replace(',','.');
//
//            expectedWin = Double.parseDouble(stringToFormat);
//
//            Long existingBetIdOrNull = getExistingBetIdOrNull(user, match, playerToBet);
//
//            if(existingBetIdOrNull == null){
//                Bet bet = new Bet(betValue,getPlayerToBet(playerToBet,match),expectedWin);
//                user.addBet(bet,match);
//                userService.save(user);
//            }
//            else{
//                for(var bet : filteredMatch.getBets()){
//                    if(existingBetIdOrNull.equals(bet.getId())){
//                        bet.setAmount(betValue);
//                        bet.setExpectedWin(expectedWin);
//                    }
//                }
//                userService.save(user);
//            }
//
//            matchToEditUrl = match;
//        }
//
//        if(matchToEditUrl!=null){
//            return "redirect:/?round="+matchToEditUrl.getRound();
//        }
//        else{
//            return "redirect:/";
//        }
//    }

    @GetMapping("/processPointsSubmission")
    public String processPointsSubmission(@RequestParam("arguments") String arguments) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            user = userService.findByUserName(username);
        }

        Match matchToEditUrl = null;
        String[] parts = arguments.split(";");
        for (String part : parts) {
            processArgumentPart(part, user);
            matchToEditUrl = matchService.findById(Long.parseLong(part.substring(part.indexOf("inputMatch") + 10, part.indexOf("player"))));
        }

        return matchToEditUrl != null ? "redirect:/?round=" + matchToEditUrl.getRound() : "redirect:/";
    }

    private void processArgumentPart(String part, User user) {
        double betValue = Double.parseDouble(part.substring(0, part.indexOf("inputMatch")));
        Long matchId = Long.parseLong(part.substring(part.indexOf("inputMatch") + 10, part.indexOf("player")));
        String playerToBet = part.contains("player1") ? "player1" : "player2";
        Match match = matchService.findById(matchId);
        Match filteredMatch = Match.filterMatchByUser(match, user);
        processBet(user, filteredMatch, playerToBet, betValue);
    }
    private void processBet(User user, Match match, String playerToBet, double betValue) {
        Double expectedWin = getOdds(playerToBet, match) * betValue;
        expectedWin = Double.parseDouble(String.format("%.5f", expectedWin).replace(',', '.'));

        Long existingBetIdOrNull = getExistingBetIdOrNull(user, match, playerToBet);

        if (existingBetIdOrNull == null) {
            Bet bet = new Bet(betValue, getPlayerToBet(playerToBet, match), expectedWin);
            user.addBet(bet, match);
        } else {
            for (var bet : match.getBets()) {
                if (existingBetIdOrNull.equals(bet.getId())) {
                    bet.setAmount(betValue);
                    bet.setExpectedWin(expectedWin);
                }
            }
        }
        userService.save(user);
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
    private Long getExistingBetIdOrNull(User user,Match match,String playerToBetString){
        Player playerToBet = getPlayerToBet(playerToBetString,match);
        Bet bet = betService.findBetByUserIdAndMatchToBetIdAndBetOnPlayerId(user.getId(), match.getId(),playerToBet.getId());

        return (bet == null) ? null : bet.getId();
    }

}
