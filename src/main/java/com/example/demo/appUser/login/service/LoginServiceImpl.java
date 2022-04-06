package com.example.demo.appUser.login.service;

import com.example.demo.appUser.repository.AppUserRepository;
import com.example.demo.appUser.repository.models.AppUserInDB;
import com.example.demo.appUser.security.dependency.JWTUtils;
import com.example.demo.appUser.user.service.AppUserServiceImpl;
import com.example.demo.appUser.registration.dependencies.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final EmailValidator emailValidator;
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtils jwtUtils;


    public String login(String email, String password) {
        boolean isValidEmail = emailValidator.test(email);
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        return loginUser(email, password);
    }

    private String loginUser(String email, String password) {
        Optional<AppUserInDB> optionalAppUser = appUserRepository.findByEmail(email);
        boolean userExists = optionalAppUser.isPresent();
        if (userExists) {
            boolean isPasswordSame = bCryptPasswordEncoder.matches(password, optionalAppUser.get().getPassword());
            if (!isPasswordSame) {
                throw new IllegalStateException("Incorrect password");
            }
            return jwtUtils.createJWTToken(email, optionalAppUser.get().getAppUserRole());
        }
        throw new IllegalStateException("User not found");
    }
}
