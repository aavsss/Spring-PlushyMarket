package com.example.demo.user.models;

import com.example.demo.authorization.appuser.models.AppUserRole;
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
