package com.lowthink.lowThinkJava.config;

import com.lowthink.lowThinkJava.entity.LoginFailure;
import com.lowthink.lowThinkJava.repository.LoginFailureRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private LoginFailureRepository loginFailureRepository;

    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final int LOCK_TIME_MINUTES = 30;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, 
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        if (username != null) {
            LoginFailure loginFailure = loginFailureRepository.findByUsername(username);
            int failureCount = loginFailure != null ? loginFailure.getFailureCount() + 1 : 1;
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lockUntil = failureCount >= MAX_FAILED_ATTEMPTS ? now.plusMinutes(LOCK_TIME_MINUTES) : null;

            loginFailureRepository.updateFailureCount(username, failureCount, now, lockUntil);

            // 如果账号被锁定，设置锁定消息
            if (lockUntil != null) {
                request.getSession().setAttribute("accountLocked", true);
                request.getSession().setAttribute("lockUntil", lockUntil);
            }
        }

        // 设置验证码显示标志
        request.getSession().setAttribute("showCaptcha", true);
        // 重定向到登录页面并显示错误信息
        response.sendRedirect("/login?error");
    }
}