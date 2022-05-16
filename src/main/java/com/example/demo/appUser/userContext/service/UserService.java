package com.example.demo.appUser.userContext.service;

import com.example.demo.appUser.userContext.models.SellingAnalytics;
import com.example.demo.appUser.userContext.models.UserContext;

public interface UserService {
    UserContext getUserContext(String jwtToken);

    SellingAnalytics getSellerAnalyticsOf(String jwtToken);

}
