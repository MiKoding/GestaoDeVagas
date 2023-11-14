package com.mikaioyamada.Gestao_Vagas.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean //sobreescreve uma implementação existente
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info().title("Gestão de Vagas").description("API resposável pela gestão de vagas").version("1"))
                .schemaRequirement("jwt_auth", createSecurityScheme());

    }
    private SecurityScheme createSecurityScheme(){
        return new SecurityScheme().name("jwt_auth").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
    }
}
