package com.example.demo.authorization.security.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

@Service
public class CheckAuthCookieFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        MutableHttpServletRequest mutableHttpServletRequest = new MutableHttpServletRequest(httpServletRequest);

        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie: cookies) {
                logger.debug(cookie.getName() + " : " + cookie.getValue());
                if (cookie.getName().equalsIgnoreCase("token")) {
                    mutableHttpServletRequest.putHeader("Authorization", URLDecoder.decode(cookie.getValue(), "UTF-8"));
                }
            }
        }

        filterChain.doFilter(mutableHttpServletRequest, servletResponse);
    }
}
