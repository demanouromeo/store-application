package com.dmsacad.store.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dmsacad.store.entities.Role;
import com.dmsacad.store.filters.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        //Stateless sessions (token-based authentication - In constrast with session-based authentication)
        //Disable CSRF protection (Cross Site Request Forgery) - CSRF is not needed on REST to improve performance
        // Authorize some endpoint
        http
                .sessionManagement(c
                        -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)//To tell spring our authentication Token-Based

                )
                .csrf(AbstractHttpConfigurer::disable)//OR .csrf(c->c.disable()) // To disable CSRF
                .authorizeHttpRequests(c -> c
                        .requestMatchers("/carts/**").permitAll()
                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())//To allow only ADMIN to access /admin endpoints
                        .requestMatchers(HttpMethod.POST, "/users").permitAll() //TO enable only POST request on User
                        .requestMatchers(HttpMethod.POST, "/products").permitAll()
                        .requestMatchers(HttpMethod.POST, "/category").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.POST, "/checkout/webhook").permitAll()//STRIPE is not a user. We can't as strippe to login. instead we shall use signature
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)//addFilterBefore means add filter before build-in filters in spring security
                //With .addFilterBefore we are making all our endpoints accessible if valid token is provided
                .exceptionHandling(c
                        -> {
                    c.authenticationEntryPoint(
                            new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)); //401 qui est != 403(Forbiden)//Using 401. We won't have 403(Forbiden) When the user has not been authenticated
                    c.accessDeniedHandler((request, response, accessDeniedException)
                            -> response.setStatus(HttpStatus.FORBIDDEN.value()));
                });
        return http.build();
    }
}
