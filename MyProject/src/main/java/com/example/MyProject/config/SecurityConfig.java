package com.example.MyProject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//WebSecurityConfigurerAdapter deprecated

@Configuration
@EnableWebSecurity //Is a type of config
@RequiredArgsConstructor //Creates a constructor that requires our fields with final, optionally also @NonNull. Constructor sets these fields.
public class SecurityConfig {

    //Final makes it automatically injected by Spring
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

        /*
        On application startup, Spring Security will look for a bean of type SecurityFilterChain.
        This SecurityFilterChain bean will be used instead of default SecurityFilterChain bean
        The SecurityFilterChain is the bean responsible for configuring all HTTP security for the application.

        This configure method is where you specify:
            1. What URLs to protect (authenticated()) and which ones are allowed (permitAll()).
            2. Which authentication methods are allowed (formLogin(), httpBasic()) and how they are configured.
            3. In short: your application’s complete security configuration.
        */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() //Cross-site request forgery: Disable csrf verification because we are using another token mechanism (JWT) already.
            .authorizeHttpRequests().requestMatchers("/api/v1/auth/**").permitAll() //We want to permit all requests in the patterns list. This is the whitelist.
            .anyRequest().authenticated() //All other requests should be authenticated.
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Session management: our JwtAuthenticationFilter is a OncePerRequestFilter, which means EVERY request should be authenticated. This means that we should NOT store the authentication state or the session state (stateless). This helps us ensure EVERY request should be authenticated. sessionCreationPolicy(SessionCreationPolicy.STATELESS) creates a new session for every request
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); //Add our JwtAuthenticationFilter. We also want to use it BEFORE the UsernamePasswordAuthenticationFilter - that is because JwtAuthenticationFilter sets the SecurityContext, update SecurityContextHolder, and AFTER that we call the UsernamePasswordAuthenticationFilter.

        return http.build();

        /*
            //http.formLogin(); //Disable form login by commenting out
            http.httpBasic(); //Postman request > Authorization > Basic Auth. username: user, password: (Printed in terminal by Spring)
         */
    }





}
