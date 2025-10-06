package com.ofss.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/roles/**").hasRole("ADMIN")
                .requestMatchers("/banks/**").hasAnyRole("ADMIN")
                .requestMatchers("/customers/**").hasAnyRole("ADMIN", "USER")

                // Accounts CRUD: allow only admin to create/update/patch accounts or see all accounts
                .requestMatchers(HttpMethod.GET, "/accounts").hasRole("ADMIN") // Only admin can see all accounts
                .requestMatchers(HttpMethod.POST, "/accounts").hasRole("ADMIN") // Only admin can create
                .requestMatchers(HttpMethod.PUT, "/accounts/id/*").hasRole("ADMIN") // Only admin can update
                .requestMatchers(HttpMethod.PATCH, "/accounts/id/*").hasRole("ADMIN") // Only admin can partial update

// Accounts GET by ID: both admin and user, but user must only see their own in controller logic
                .requestMatchers(HttpMethod.GET, "/accounts/id/*").hasAnyRole("ADMIN", "USER")

// Transactions: both admin and user, but user must only affect their own
                .requestMatchers("/deposit/accounts/id/*").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/withdraw/accounts/id/*").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/transfer/fromSourceAccount/*/toTargetAccount/*").hasAnyRole("ADMIN", "USER")

                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
