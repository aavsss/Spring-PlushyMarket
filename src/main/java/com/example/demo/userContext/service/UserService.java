package com.example.demo.userContext.service;

import com.example.demo.userContext.models.UserContext;

public interface UserService {
    UserContext getUserContext(String jwtToken);
}
