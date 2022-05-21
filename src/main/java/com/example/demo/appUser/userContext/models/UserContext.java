package com.example.demo.appUser.userContext.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserContext {
    private String email;
    private AppUserRole appUserRole;
}
