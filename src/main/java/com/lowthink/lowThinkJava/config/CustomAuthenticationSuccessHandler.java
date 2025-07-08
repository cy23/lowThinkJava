package com.lowthink.lowThinkJava.config;

import com.lowthink.lowThinkJava.repository.LoginFailureRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LoginFailureRepository loginFailureRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
                                        Authentication authentication) throws IOException, ServletException {
        // 登录成功重置失败记录
        String username = authentication.getName();
        loginFailureRepository.resetFailure(username);
        
        // 清除验证码显示标志
        request.getSession().removeAttribute("showCaptcha");
        
        response.sendRedirect("/");
    }
}