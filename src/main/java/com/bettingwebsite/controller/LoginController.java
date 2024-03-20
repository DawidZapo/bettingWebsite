package com.bettingwebsite.controller;

import com.bettingwebsite.entity.User;
import com.bettingwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        LoginController.userService = userService;
    }

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

    public static User getLogonUserAndSetAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("username", username);

            User user = userService.findByUserName(username);
            if (user != null) {
                model.addAttribute("userDetails", user.getUserDetails());
            }
            return user;
        }
        else{
            return null;
        }
    }
}
