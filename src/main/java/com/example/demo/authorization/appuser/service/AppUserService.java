package com.example.demo.authorization.appuser.service;

import com.example.demo.authorization.appuser.models.AppUser;

public interface AppUserService {
    String signUpUser(AppUser appUser);
    String loginUser(String email, String password);
}
