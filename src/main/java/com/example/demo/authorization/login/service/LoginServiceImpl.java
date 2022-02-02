package com.example.demo.authorization.login.service;

import com.example.demo.authorization.appuser.service.AppUserServiceImpl;
import com.example.demo.authorization.registration.dependencies.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final AppUserServiceImpl appUserService;
    private final EmailValidator emailValidator;

    public String login(String email, String password) {
        boolean isValidEmail = emailValidator.test(email);
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        return appUserService.loginUser(email, password);
    }
}
