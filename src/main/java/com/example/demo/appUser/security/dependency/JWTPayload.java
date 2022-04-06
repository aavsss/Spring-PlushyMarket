package com.example.demo.appUser.security.dependency;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class JWTPayload {
    private String email;
    private List<String> authorities;
    private Integer iat;
    private Integer exp;
}
