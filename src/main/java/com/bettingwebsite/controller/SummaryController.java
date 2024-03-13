package com.bettingwebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SummaryController {

    @GetMapping("/summary")
    public String showSummary(Model model){
        return "summary";
    }
}
