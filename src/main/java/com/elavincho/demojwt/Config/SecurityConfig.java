package com.elavincho.demojwt.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
//import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.elavincho.demojwt.Jwt.JwtAutenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public final JwtAutenticationFilter jwtAutenticationFilter;
    public final AuthenticationProvider authProvider;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
        .csrf(csrf ->
        csrf
        .disable())
        .authorizeHttpRequests(authRequest ->
            authRequest
            .requestMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
            )
        .sessionManagement(sessionManager ->
        sessionManager
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authProvider)
        .addFilterBefore(jwtAutenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }
}
