package com.example.demo.userContext.controller;

import com.example.demo.userContext.models.SellingAnalytics;
import com.example.demo.userContext.models.UserContext;
import com.example.demo.userContext.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/context")
    public UserContext getUserContext(
            @CookieValue(name = "token", defaultValue = "token") String jwtToken
    ) {
        return userService.getUserContext(jwtToken);
    }

    @GetMapping(path = "/selling-analytics")
    public SellingAnalytics getSellingAnalytics(
            @CookieValue(name = "token", defaultValue = "token") String jwtToken
    ) {
        return userService.getSellerAnalyticsOf(jwtToken);
    }
}
