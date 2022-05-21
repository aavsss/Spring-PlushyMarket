package com.example.demo.globalService.stringValidator;

import org.springframework.stereotype.Service;

@Service
public class StringValidatorImpl implements StringValidator {

    @Override
    public boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
