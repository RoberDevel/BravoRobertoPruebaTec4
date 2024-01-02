package com.roberdev.gestionturismo.config;

import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.roberdev.gestionturismo.model.enums.FlightSeatType;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.postConfigurer(objectMapper -> {
            objectMapper.coercionConfigFor(FlightSeatType.class)
                    .setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsNull);
        });
    }
}
