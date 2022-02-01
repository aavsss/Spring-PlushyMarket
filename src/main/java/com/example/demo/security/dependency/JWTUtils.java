package com.example.demo.security.dependency;

import com.example.demo.appuser.models.AppUserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JWTUtils {

    public String getJWTToken(String email) {
        String secretKey = "mySecretKeyholaholaholaholaholamySecretKeyholaholaholaholaholamySecretKeyholaholahola";
        List<GrantedAuthority> grantedAuthorityList = AuthorityUtils
                .commaSeparatedStringToAuthorityList(AppUserRole.USER.toString());

        String token = Jwts
                .builder()
                .setId("myJWT")
                .setSubject(email)
                .claim("authorities",
                        grantedAuthorityList.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
