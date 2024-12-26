package com.skv.cloud.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().permitAll() // Allow unauthenticated access to all endpoints
                )
                .csrf().disable() // Disable CSRF protection if not needed
                .formLogin().disable() // Disable the default login page
                .logout().disable(); // Disable the default logout functionality

        return http.build();
    }

}
