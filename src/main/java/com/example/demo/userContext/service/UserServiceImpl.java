package com.example.demo.userContext.service;

import com.example.demo.appUser.security.dependency.JWTPayload;
import com.example.demo.appUser.security.dependency.JWTUtils;
import com.example.demo.appUser.user.models.AppUserRole;
import com.example.demo.userContext.models.SellingAnalytics;
import com.example.demo.userContext.models.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final JWTUtils jwtUtils;

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
        return new SellingAnalytics(
                1, 0, 5, 0L);
    }
}
