package com.example.demo.appUser.registration.service;

import com.example.demo.appUser.registration.models.RegistrationRequest;

public interface RegistrationService {
    String register(RegistrationRequest request);

    String registerAsSeller(RegistrationRequest request);
}
