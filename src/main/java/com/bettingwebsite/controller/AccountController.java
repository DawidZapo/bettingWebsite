package com.bettingwebsite.controller;

import com.bettingwebsite.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/account")
    public String showAccount(Model model){
        User user = LoginController.getLogonUserAndSetAttributes(model);
        model.addAttribute("user", user);
        return "account";
    }
}
