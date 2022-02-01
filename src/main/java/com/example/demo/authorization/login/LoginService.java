package com.example.demo.authorization.login;

import com.example.demo.authorization.appuser.service.AppUserService;
import com.example.demo.authorization.registration.dependencies.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {
    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String login(String email, String password) {
        boolean isValidEmail = emailValidator.test(email);
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        return appUserService.loginUser(email, password);
    }
}
