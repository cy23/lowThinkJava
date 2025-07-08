package com.lowthink.lowThinkJava.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class CaptchaController {

    @GetMapping("/captcha")
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 使用Hutool生成图形验证码，宽150，高45，4位数字，5条干扰线
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(150, 45, 4, 5);
        // 将验证码存入session
        request.getSession().setAttribute("captchaCode", captcha.getCode());
        // 设置响应头，禁止缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 直接输出验证码图片
        captcha.write(response.getOutputStream());
    }
}