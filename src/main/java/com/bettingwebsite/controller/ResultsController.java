package com.bettingwebsite.controller;

import com.bettingwebsite.entity.User;
import com.bettingwebsite.service.UserService;
import com.bettingwebsite.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ResultsController {
    private MatchService matchService;
    private UserService userService;

    @Autowired
    public ResultsController(MatchService matchService, UserService userService) {
        this.matchService = matchService;
        this.userService = userService;
    }

    @GetMapping("/results")
    public String showResults(Model model){
        User user = LoginController.getLogonUserAndSetAttributes(model);

        List<User> users = userService.findAllExceptAdminAndDisabled();
        model.addAttribute("users", users);


        return "results";
    }
}
