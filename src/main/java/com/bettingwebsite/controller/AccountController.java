package com.bettingwebsite.controller;

import com.bettingwebsite.entity.User;
import com.bettingwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/account")
    public String showAccount(Model model, @RequestParam(name = "success",required = false)Boolean success){
        User user = LoginController.getLogonUserAndSetAttributes(model);
        if(success == null){
            success = false;
        }
        model.addAttribute("success", success);
        model.addAttribute("user", user);
        return "account";
    }

    @PostMapping("/account/changePassword")
    public String changePassword(@RequestParam("oldPassword")String oldPassword,
                                 @RequestParam("newPassword")String newPassword,
                                 @RequestParam("confirmPassword")String confirmPassword,
                                 @RequestParam("userId")Long userId){

        User user = userService.findById(userId);
        if(!BCrypt.checkpw(oldPassword,user.getPassword())){
            throw new RuntimeException("Old password does not match within the form");
        }
        if(!newPassword.equals(confirmPassword)){
            throw new RuntimeException("New password and confirm password does not match");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);

        return "redirect:/account?success=true";
    }
}
