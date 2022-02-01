package com.example.demo.registration.controller;

import com.example.demo.registration.models.RegistrationRequest;
import com.example.demo.registration.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public String register(
            @RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

}
