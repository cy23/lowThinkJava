package com.lowthink.lowThinkJava.config;

import com.lowthink.lowThinkJava.entity.LoginFailure;
import com.lowthink.lowThinkJava.repository.LoginFailureRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

public class AccountLockCheckFilter extends OncePerRequestFilter {

    private final LoginFailureRepository loginFailureRepository;
    private final AuthenticationFailureHandler failureHandler;

    public AccountLockCheckFilter(LoginFailureRepository loginFailureRepository, AuthenticationFailureHandler failureHandler) {
        this.loginFailureRepository = loginFailureRepository;
        this.failureHandler = failureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 仅处理登录POST请求
        if ("/login".equals(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
            String username = request.getParameter("username");
            if (username != null) {
                LoginFailure loginFailure = loginFailureRepository.findByUsername(username);
                if (loginFailure != null && loginFailure.getLockUntil() != null) {
                    LocalDateTime now = LocalDateTime.now();
                    // 检查账号是否仍处于锁定状态
                    if (now.isBefore(loginFailure.getLockUntil())) {
                        AuthenticationException exception = new AuthenticationServiceException(
                            "账号已被锁定，请于" + loginFailure.getLockUntil() + "后再试");
                        failureHandler.onAuthenticationFailure(request, response, exception);
                        return;
                    } else if (now.isAfter(loginFailure.getLockUntil())) {
                        // 锁定时间已过，重置失败记录
                        loginFailureRepository.resetFailure(username);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}