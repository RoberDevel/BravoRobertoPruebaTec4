package com.roberdev.gestionturismo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.GET, "/agency/hotels").permitAll()
                                .requestMatchers(HttpMethod.GET, "/agency/hotels/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/agency/flights").permitAll()
                                .requestMatchers(HttpMethod.GET, "/agency/flights/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/agency/hotel-booking/new").permitAll()
                                .requestMatchers(HttpMethod.POST, "/agency/flight-booking/new").permitAll()
                                .anyRequest().authenticated()

                )
                .formLogin(login -> login
                        .permitAll()
                )
                .httpBasic().and()
                .build();
    }
}
/*
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/agency/flights").permitAll()
                                .requestMatchers("/agency/flights/{id}").permitAll()
                                .requestMatchers("/agency/flight-booking/new").permitAll()
                                .requestMatchers("/agency/hotels").permitAll()
                                .requestMatchers("/agency/hotels/{id}").permitAll()
                                .requestMatchers("/agency/hotel-booking/new").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .permitAll()
                )
                .httpBasic().and()
                .build();
    }

}*/
