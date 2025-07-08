package com.lowthink.lowThinkJava.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DashboardController {

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardData(Authentication authentication) {
        Map<String, Object> dashboardData = new HashMap<>();
        dashboardData.put("message", "Welcome to Admin Dashboard");
        dashboardData.put("username", authentication.getName());
        dashboardData.put("roles", authentication.getAuthorities());
        return dashboardData;
    }
}