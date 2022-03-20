package com.example.demo.authorization.registration.service;

import com.example.demo.authorization.appuser.repository.models.AppUserInDB;
import com.example.demo.authorization.appuser.models.AppUserRole;
import com.example.demo.authorization.appuser.service.AppUserServiceImpl;
import com.example.demo.authorization.registration.dependencies.EmailValidator;
import com.example.demo.authorization.registration.models.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService{

    private final AppUserServiceImpl appUserService;
    private final EmailValidator emailValidator;

    @Override
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        return appUserService.signUpUser(
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
        return appUserService.signUpUser(
                new AppUserInDB(
                        request.getFirstName(),
                        request.getFirstName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.SELLER
                )
        );
    }
}
