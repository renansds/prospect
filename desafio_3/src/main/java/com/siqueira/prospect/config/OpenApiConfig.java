package com.siqueira.prospect.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Desafio 1",
        version = "1.0",
        description= "API responsável pelo pré-cadastro de clientes.",
        termsOfService = "https://exemplo.com/termos-de-aceitacao",
        contact = @Contact(
                name = "Renan Siqueira",
                email = "renan.santosdesiqueira@gmail.com",
                url = "https://github.com/renansds?tab=repositories"
        ),
        license = @License(
                name = "Licença Apache 2.0",
                url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
))
public class OpenApiConfig {}