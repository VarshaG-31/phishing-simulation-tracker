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
                .csrf(csrf -> csrf.disable()) // Disable CSRF for local endpoint testing
                .authorizeHttpRequests(auth -> auth
                        // Day 9 & 10 Public Paths: Open up Swagger documentation and basic health check
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/actuator/health").permitAll()

                        // Secure all other Actuator metrics endpoints and your core APIs
                        .requestMatchers("/actuator/**").authenticated()
                        .requestMatchers("/api/v1/simulations/**").authenticated()

                        // Fallback security rule
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

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