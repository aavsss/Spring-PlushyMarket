package com.example.demo.authorization.registration.controller;

import com.example.demo.authorization.registration.models.RegistrationRequest;
import com.example.demo.authorization.registration.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public String register(
            @RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

}
