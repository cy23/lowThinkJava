package com.lowthink.lowThinkJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model) {
        // 将验证码显示标志传递给模板
        model.addAttribute("showCaptcha", session.getAttribute("showCaptcha") != null);
        return "login";
    }
}