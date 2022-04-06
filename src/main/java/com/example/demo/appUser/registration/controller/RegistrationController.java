package com.example.demo.appUser.registration.controller;

import com.example.demo.appUser.registration.models.RegistrationRequest;
import com.example.demo.appUser.registration.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.example.demo.helper.Constants.ENCODING_STD;

@RestController
@RequestMapping(path = "api/v1/registration")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping(path = "/seller")
    public String registerAsSeller(
            @RequestBody RegistrationRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException {
        String token = registrationService.registerAsSeller(request);

        Cookie cookie = new Cookie("token", URLEncoder.encode(token, ENCODING_STD));
        cookie.setSecure(false);
        cookie.setHttpOnly(false);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        return token;
    }

    @PostMapping
    public String register(
            @RequestBody RegistrationRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException {
        String token = registrationService.register(request);

        Cookie cookie = new Cookie("token", URLEncoder.encode(token, ENCODING_STD));
        cookie.setSecure(false);
        cookie.setHttpOnly(false);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        return token;
    }


}
