package com.edustream.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Looks up templates/login.html
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard"; // Looks up templates/dashboard.html
    }
}
