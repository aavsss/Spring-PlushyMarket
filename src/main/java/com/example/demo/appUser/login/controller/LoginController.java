package com.example.demo.appUser.login.controller;

import com.example.demo.appUser.login.model.LoginRequestBody;
import com.example.demo.appUser.login.service.LoginService;
import com.example.demo.appUser.registration.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@RestController
@RequestMapping(path = "api/v1/login")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final RegistrationService registrationService;

    @PostMapping
    public String login(
            @RequestBody LoginRequestBody requestBody,
            HttpServletResponse response) throws UnsupportedEncodingException {

        String token = loginService.login(requestBody.getEmail(), requestBody.getPassword());

        Cookie cookie = new Cookie("token", URLEncoder.encode(token, "UTF-8"));
        cookie.setSecure(false);
        cookie.setHttpOnly(false);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        return token;
    }

}
