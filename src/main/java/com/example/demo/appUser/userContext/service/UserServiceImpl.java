package com.example.demo.appUser.userContext.service;

import com.example.demo.appUser.repository.AppUserRepository;
import com.example.demo.appUser.security.dependency.JWTPayload;
import com.example.demo.appUser.security.dependency.JWTUtils;
import com.example.demo.appUser.userContext.models.AppUserRole;
import com.example.demo.appUser.userContext.models.SellingAnalytics;
import com.example.demo.appUser.userContext.models.UserContext;
import com.example.demo.crud.repository.models.PlushyInDB;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.demo.helper.Constants.USER_NOT_FOUND_MSG;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final JWTUtils jwtUtils;
    private final AppUserRepository appUserRepository;
    private final SellingAnalyticsService sellingAnalyticsService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    @Override
    public UserContext getUserContext(String jwtToken) {
        JWTPayload jwtPayload = jwtUtils.decodeJWTToken(jwtToken);
        if (jwtPayload != null) {
            return new UserContext(
                    jwtPayload.getEmail(),
                    AppUserRole.valueOf(jwtPayload.getAuthorities().get(0))
            );
        }
        return null;
    }

    @Override
    public SellingAnalytics getSellerAnalyticsOf(String jwtToken) {
        JWTPayload jwtPayload = jwtUtils.decodeJWTToken(jwtToken);
        Optional<List<PlushyInDB>> optionalSellingAnalytics = appUserRepository.getSellingAnalytics(
                jwtPayload.getEmail()
        );
        if (optionalSellingAnalytics.isPresent()){
            SellingAnalytics sellingAnalytics = new SellingAnalytics();

            sellingAnalytics.setActive(
                    optionalSellingAnalytics.get().size()
            );
            sellingAnalytics.setTotalQuantitiesSold(
                    sellingAnalyticsService.calculateTotalQuantitiesSold(optionalSellingAnalytics.get())
            );
            sellingAnalytics.setTotalQuantitiesAvailable(
                    sellingAnalyticsService.calculateTotalQuantitiesAvailable(optionalSellingAnalytics.get())
            );
            sellingAnalytics.setTotalMoneyEarned(
                    sellingAnalyticsService.calculateMoneyEarned(optionalSellingAnalytics.get())
            );
            return sellingAnalytics;
        }

        throw new IllegalStateException("Plushies of owner not found");
    }

    private int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    @Override
    public Integer getTotalNumberOfUsers(String jwtToken) {
        return appUserRepository.getTotalNumberOfUsers();
    }
}
