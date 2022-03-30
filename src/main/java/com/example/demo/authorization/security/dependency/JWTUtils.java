package com.example.demo.authorization.security.dependency;

import com.example.demo.authorization.appuser.models.AppUserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.helper.Constants.JWT_SECRET_KEY;

@Service
@AllArgsConstructor
public class JWTUtils {

    @Autowired
    private Environment env;

    public String createJWTToken(String email, AppUserRole appUserRole) {
        String secretKey = env.getProperty(JWT_SECRET_KEY);
        List<GrantedAuthority> grantedAuthorityList = AuthorityUtils
                .commaSeparatedStringToAuthorityList(appUserRole.name());

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("authorities", grantedAuthorityList.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        String token = Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 900000000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    public JWTPayload decodeJWTToken(String token) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        token = token.substring(7);
        String[] chunks = token.split("\\.");
        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        ObjectMapper mapper = new ObjectMapper();
        try{
            Map<String, Object> map = mapper.readValue(payload, Map.class);
            JWTPayload jwtPayload = new JWTPayload();
            jwtPayload.setEmail((String) map.get("email"));
            jwtPayload.setAuthorities((List<String>) map.get("authorities"));
            jwtPayload.setIat((Integer) map.get("iat"));
            jwtPayload.setExp((Integer) map.get("exp"));
            return jwtPayload;
        } catch (JsonProcessingException e) {
            System.out.println("exception while processing json " + e.getMessage());
        }
        return null;
    }
}
