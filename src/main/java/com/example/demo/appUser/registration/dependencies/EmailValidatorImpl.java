package com.example.demo.appUser.registration.dependencies;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailValidatorImpl implements EmailValidator, Predicate<String> {
    private final Pattern validEmailAddressRegex =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean test(String email) {
        Matcher matcher = validEmailAddressRegex.matcher(email);
        return matcher.find();
    }
}
