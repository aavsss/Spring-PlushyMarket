package com.example.demo.authorization.registration.controller;

import com.example.demo.authorization.registration.models.RegistrationRequest;
import com.example.demo.authorization.registration.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.example.demo.helper.Constants.ENCODING_STD;

@RestController
@RequestMapping(path = "api/v1/registrationSeller")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
@AllArgsConstructor
public class RegistrationSellerController {

    private RegistrationService registrationService;

    @PostMapping
    public String registerAsSeller(
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