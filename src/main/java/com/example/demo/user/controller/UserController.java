package com.example.demo.user.controller;

import com.example.demo.authorization.appuser.models.AppUserRole;
import com.example.demo.authorization.security.dependency.JWTPayload;
import com.example.demo.authorization.security.dependency.JWTUtils;
import com.example.demo.user.models.UserContext;
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
        return new UserContext(
                jwtPayload.getEmail(),
                AppUserRole.valueOf(jwtPayload.getAuthorities().get(0))
        );
    }
}
