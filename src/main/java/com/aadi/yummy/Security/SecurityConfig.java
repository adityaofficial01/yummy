package com.aadi.yummy.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(e -> e.disable())
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/api/v1/auth/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                                .anyRequest().authenticated());
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        http.exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("user")                                                   
                        .password("{noop}user")
                        .roles("USER")
                        .build()
        );
    }



    

    //   public UserDetailsService userDetailsService(UserRepo userRepo) {
    //     return email -> {
    //         com.aadi.yummy.entities.User user = userRepo.findByEmail(email)
    //                 .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    //         return User.builder()
    //                 .username(user.getEmail())
    //                 .password("{noop}" + user.getPassword())
    //                 .roles(user.getRole() != null ? user.getRole().name() : "CUSTOMER")
    //                 .build();
    //     };
    // }



}
