package com.example.demo.authorization.login;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public String login(
            @RequestBody LoginRequestBody requestBody) {
        return loginService.login(requestBody.getEmail(), requestBody.getPassword());
    }
}
