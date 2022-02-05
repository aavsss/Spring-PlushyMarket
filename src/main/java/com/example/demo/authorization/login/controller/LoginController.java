package com.example.demo.authorization.login.controller;

import com.example.demo.authorization.login.model.LoginRequestBody;
import com.example.demo.authorization.login.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@RestController
@RequestMapping(path = "api/v1/login")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

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

    @GetMapping
    public String readCookie(
            @CookieValue(name = "token", defaultValue = "default value") String token
    ) {
        System.out.println("token " + token);
        return token;
    }

    @GetMapping("/create")
    public String createCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("foo", "bar");
        cookie.setSecure(false);
        cookie.setHttpOnly(false);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
        return "cookie is added!";
    }
}
