package com.example.demo.appUser.userContext.service;

import com.example.demo.appUser.repository.AppUserRepository;
import com.example.demo.appUser.security.dependency.JWTPayload;
import com.example.demo.appUser.security.dependency.JWTUtils;
import com.example.demo.appUser.user.models.AppUserRole;
import com.example.demo.appUser.userContext.models.SellingAnalytics;
import com.example.demo.appUser.userContext.models.UserContext;
import com.example.demo.crud.repository.models.PlushyInDB;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final JWTUtils jwtUtils;
    private final AppUserRepository appUserRepository;
    private final SellingAnalyticsService sellingAnalyticsService;

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


}
