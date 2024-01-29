package com.bettingwebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/loginPage")
    public String showMyLoginPage() {
        return "login/login-form";
    }

    @GetMapping("/accessDenied")
    public String showAccessDenied(){
        return "error/access-denied";
    }

    @GetMapping("/pageNotFound")
    public String pageNotFound(){
        return "error/page-not-found";
    }
}
