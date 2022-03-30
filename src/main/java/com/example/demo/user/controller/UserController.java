package com.example.demo.user.controller;

import com.example.demo.authorization.appuser.models.AppUserRole;
import com.example.demo.authorization.security.dependency.JWTPayload;
import com.example.demo.authorization.security.dependency.JWTUtils;
import com.example.demo.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/user")
@AllArgsConstructor
public class UserController {

//    private final UserService userService;
    private final JWTUtils jwtUtils;

    @GetMapping("/role")
    public AppUserRole getUserRole(
            @CookieValue(name = "token", defaultValue = "token") String jwtToken
    ) {
        JWTPayload jwtPayload = jwtUtils.decodeJWTToken(jwtToken);
        return AppUserRole.valueOf(jwtPayload.getAuthorities().get(0));
    }
}
