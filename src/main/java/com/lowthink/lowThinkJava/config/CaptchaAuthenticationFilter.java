package com.lowthink.lowThinkJava.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CaptchaAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationFailureHandler failureHandler;

    public CaptchaAuthenticationFilter(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 仅处理登录POST请求
        if ("/login".equals(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
            HttpSession session = request.getSession();
            Boolean showCaptcha = (Boolean) session.getAttribute("showCaptcha");

            if (showCaptcha != null && showCaptcha) {
                String captchaCode = (String) session.getAttribute("captchaCode");
                String userInputCaptcha = request.getParameter("captcha");

                if (captchaCode == null || userInputCaptcha == null || !captchaCode.equalsIgnoreCase(userInputCaptcha)) {
                    // 验证码验证失败
                    AuthenticationException exception = new AuthenticationServiceException("验证码错误或已过期");
                    failureHandler.onAuthenticationFailure(request, response, exception);
                    return;
                }
                // 验证成功后移除验证码
                session.removeAttribute("captchaCode");
            }
        }

        filterChain.doFilter(request, response);
    }
}