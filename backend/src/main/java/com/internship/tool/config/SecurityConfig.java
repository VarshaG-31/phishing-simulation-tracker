package com.internship.tool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enables @PreAuthorize role checks
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for Postman testing
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/simulations/**").authenticated() // Protects your API
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults()); // Enables Basic Auth (the Postman login box)

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Creating an Admin user for testing Day 6 functionality
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password123")
                .roles("ADMIN") // This grants the role required by your Controller
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}