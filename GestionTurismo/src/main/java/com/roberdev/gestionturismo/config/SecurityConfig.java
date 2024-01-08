package com.roberdev.gestionturismo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                                .requestMatchers("/agency/flights").permitAll()
                                .requestMatchers("/agency/flights/{id}").permitAll()
                                .requestMatchers("/agency/flight-booking/new").permitAll()
                                .requestMatchers("/agency/hotels").permitAll()
                                .requestMatchers("/agency/hotels/{id}").permitAll()
                                .requestMatchers("/agency/hotel-booking/new").permitAll()
                                .requestMatchers("/agency/rooms").permitAll()
                                .requestMatchers("/agency/flight-booking/all").permitAll()
                                .requestMatchers("/agency/flight-booking/cancel/{id}").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .permitAll()
                )
                .httpBasic().and()
                .build();
    }

}
