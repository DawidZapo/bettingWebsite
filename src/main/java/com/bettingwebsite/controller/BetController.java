package com.bettingwebsite.controller;

import com.bettingwebsite.entity.Bet;
import com.bettingwebsite.entity.Match;
import com.bettingwebsite.entity.User;
import com.bettingwebsite.service.UserService;
import com.bettingwebsite.service.bet.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Controller
public class BetController {
    private UserService userService;
    private BetService betService;

    @Autowired
    public BetController(UserService userService, BetService betService) {
        this.userService = userService;
        this.betService = betService;
    }

    @GetMapping("/bets")
    public String showBets(Model model){
        User user = LoginController.getLogonUserAndSetAttributes(model);

        List<Bet> bets = betService.findByUserId(user.getId());
        Comparator<Bet> betComparator = Comparator.comparing(bet -> bet.getMatch().getMatchDate());
        betComparator = betComparator.thenComparing(bet -> bet.getMatch().getMatchTime());
        bets.sort(betComparator);

        model.addAttribute("bets", bets);
        model.addAttribute("matchForHTML", new Match());

        List<String> rounds = bets.stream().map(bet -> bet.getMatch().getRound()).distinct().toList();

        model.addAttribute("rounds", rounds);

        return "bets";
    }

    @PostMapping("/bets/delete")
    public String deleteBet(@RequestParam("id")Long id){
        Bet bet = betService.findById(id);

        LocalTime timeNow = LocalTime.now();
        LocalDate dateNow = LocalDate.now();

        System.out.println(bet.getMatch().getMatchTime());
        System.out.println(bet.getMatch().getMatchDate());

        if(!dateNow.isAfter(bet.getMatch().getMatchDate()) && timeNow.isBefore(bet.getMatch().getMatchTime())){
            betService.deleteById(id);
        }

        return "redirect:/bets";
    }
}
