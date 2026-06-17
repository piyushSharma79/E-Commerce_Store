package com.backend.ecommerce_store.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http

                // Disable CSRF for Postman testing
                .csrf(csrf -> csrf.disable())

                // Authorization Rules
                .authorizeHttpRequests(auth -> auth

                        // Allow all Product APIs without login
                        .requestMatchers("/products/**").permitAll()

                        // Any other endpoint requires authentication
                        .anyRequest().authenticated()
                )

                .build();
    }
}