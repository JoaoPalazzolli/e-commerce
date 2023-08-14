package br.com.project.ECommerce.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfigs {

    // http://localhost:8080/v3/api-docs
    // http://localhost:8080/swagger-ui/index.html

    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Rest Full API with Java 17 and Spring Boot 3.1.2")
                .version("v1")
                .description("API de Ecommerce")
                .termsOfService("null")
                .license(new License()
                        .name("Apache 2.0")
                        .url("null")));
    }
}
