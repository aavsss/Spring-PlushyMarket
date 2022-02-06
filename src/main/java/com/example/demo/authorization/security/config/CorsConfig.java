package com.example.demo.authorization.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
Not working as intended. Still needs researching
 */
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
//                WebMvcConfigurer.super.addCorsMappings(registry);
                registry.addMapping("api/v*/plushy/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowCredentials(true)
                        .allowedHeaders("*");

                registry.addMapping("api/v*/login/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowCredentials(true)
                        .allowedHeaders("*");
            }
        };
    }
}
