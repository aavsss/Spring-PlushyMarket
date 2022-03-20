package com.example.demo.authorization.appuser.service;

import com.example.demo.authorization.appuser.repository.models.AppUserInDB;

public interface AppUserService {
    String signUpUser(AppUserInDB appUserInDB);
    String loginUser(String email, String password);
}
