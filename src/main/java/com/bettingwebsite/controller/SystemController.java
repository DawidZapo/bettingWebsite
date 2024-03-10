package com.bettingwebsite.controller;

import com.bettingwebsite.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemController {

    @GetMapping("/systems")
    public String showSystems(Model model){
        User user = LoginController.getLogonUserAndSetAttributes(model);
        return "systems";
    }
}
