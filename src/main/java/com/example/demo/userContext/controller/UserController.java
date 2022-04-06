package com.example.demo.userContext.controller;

import com.example.demo.appUser.security.dependency.JWTPayload;
import com.example.demo.appUser.security.dependency.JWTUtils;
import com.example.demo.appUser.user.models.AppUserRole;
import com.example.demo.userContext.models.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/user")
@AllArgsConstructor
public class UserController {

    private final JWTUtils jwtUtils;

    @GetMapping("/context")
    public UserContext getUserContext(
            @CookieValue(name = "token", defaultValue = "token") String jwtToken
    ) {
        JWTPayload jwtPayload = jwtUtils.decodeJWTToken(jwtToken);
        if (jwtPayload != null) {
            return new UserContext(
                    jwtPayload.getEmail(),
                    AppUserRole.valueOf(jwtPayload.getAuthorities().get(0))
            );
        }
        return null;
    }
}
