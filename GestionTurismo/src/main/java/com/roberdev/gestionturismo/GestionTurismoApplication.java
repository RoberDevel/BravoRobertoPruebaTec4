package com.roberdev.gestionturismo;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestionTurismoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionTurismoApplication.class, args);


    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestion Turismo API")
                        .version("0.0.1")
                        .description("Documentación de la API de Gestion Turismo con todos los endpoints")
                        .termsOfService("http://swagger.io/terms/")

                );
        //.termsOfService("http://swagger.io/terms/")
        //.license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
