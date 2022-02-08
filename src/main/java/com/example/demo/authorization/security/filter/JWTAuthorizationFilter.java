package com.example.demo.authorization.security.filter;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.helper.Constants.ENCODING_STD;
import static com.example.demo.helper.Constants.JWT_SECRET_KEY;

@Service
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private Environment env;

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            if (checkJWTToken(request, response)) {
                Claims claims = validateToken(request);
                if (claims.get("authorities") != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            System.out.println("/// e error " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
    }

    private Claims validateToken(HttpServletRequest request) throws UnsupportedEncodingException {
        String token = request.getHeader(HEADER);
        String secret = env.getProperty(JWT_SECRET_KEY);
        if (request.getCookies() != null) {
            token = getAuthTokenFromCookie(request.getCookies());
        }
        String jwtToken = token.replace(PREFIX, "");
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    private void setUpSpringAuthentication(Claims cLaims) {
        List<String> authorities = (List) cLaims.get("authorities");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                cLaims.getSubject(), null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) throws UnsupportedEncodingException {
        String token = "try";
        if (request.getCookies() != null) {
            token = getAuthTokenFromCookie(request.getCookies());
        }

        String authenticationHeader = request.getHeader(HEADER);
        if (!token.equalsIgnoreCase("try")) {
            authenticationHeader = token;
        }
        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX)) {
            return false;
        }
        return true;
    }

    private Optional<String> readCookie(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

    private String getAuthTokenFromCookie(Cookie[] cookies) throws UnsupportedEncodingException {
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equalsIgnoreCase("token")) {
                    return URLDecoder.decode(cookie.getValue(), ENCODING_STD);
                }
            }
        }
        return "try";
    }
}