package com.example.demo.authorization.appuser.service;

import com.example.demo.authorization.appuser.repository.models.AppUserInDB;
import com.example.demo.authorization.appuser.repository.AppUserRepository;
import com.example.demo.authorization.security.dependency.JWTUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements UserDetailsService, AppUserService {

    private final String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    @Override
    public String signUpUser(AppUserInDB appUserInDB) {
        boolean userExists = appUserRepository.findByEmail(appUserInDB.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUserInDB.getPassword());
        appUserInDB.setPassword(encodedPassword);

        appUserRepository.save(appUserInDB);

        return jwtUtils.createJWTToken(appUserInDB.getEmail(), appUserInDB.getAppUserRole());
    }

    @Override
    public String loginUser(String email, String password) {
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

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
