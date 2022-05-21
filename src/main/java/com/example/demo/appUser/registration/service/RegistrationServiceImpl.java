package com.example.demo.appUser.registration.service;

import com.example.demo.globalService.emailValidator.EmailValidator;
import com.example.demo.appUser.registration.models.RegistrationRequest;
import com.example.demo.appUser.repository.AppUserRepository;
import com.example.demo.appUser.repository.models.AppUserInDB;
import com.example.demo.appUser.security.dependency.JWTUtils;
import com.example.demo.appUser.userContext.models.AppUserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtils jwtUtils;
    private final EmailValidator emailValidator;

    @Override
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        return signUpUser(
                new AppUserInDB(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }

    @Override
    public String registerAsSeller(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        return signUpUser(
                new AppUserInDB(
                        request.getFirstName(),
                        request.getFirstName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.SELLER
                )
        );
    }

    private String signUpUser(AppUserInDB appUserInDB) {
        Optional<AppUserInDB> optionalAppUser = appUserRepository.findByEmail(appUserInDB.getEmail());
        boolean userExists = optionalAppUser.isPresent();
        if (userExists) {
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUserInDB.getPassword());
        appUserInDB.setPassword(encodedPassword);

        appUserRepository.save(appUserInDB);

        return jwtUtils.createJWTToken(appUserInDB.getEmail(), appUserInDB.getAppUserRole());
    }
}
