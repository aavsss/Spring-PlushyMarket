package com.example.demo.authorization.security.dependency;

import com.example.demo.authorization.appuser.models.AppUserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JWTUtils {

    @Autowired
    private Environment env;

    public String getJWTToken(String email) {
        String secretKey = env.getProperty("jet_secret_key");
        List<GrantedAuthority> grantedAuthorityList = AuthorityUtils
                .commaSeparatedStringToAuthorityList(AppUserRole.USER.toString());

//        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

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
