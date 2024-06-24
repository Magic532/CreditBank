package ru.mayorov.calculator.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * The configuration class for enabling and customizing Swagger 3 documentation
 */

@Configuration
public class SwaggerApiConfig {

    @Bean
    public GroupedOpenApi publicUserApi() {
        return GroupedOpenApi.builder()
                .group("Сalculator")
                .pathsToMatch("/calculator/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenApi(@Value("${spring.application.description}") String appDescription,
                                 @Value("${spring.application.version}") String appVersion) {
        return new OpenAPI().info(new Info().title("Application API")
                        .version(appVersion)
                        .description(appDescription)
                        .contact(new Contact().name("username")
                                .email("test@gmail.com")))
                        .servers(List.of(new Server().url("http://localhost:8080")
                                .description("Dev server")));

    }
}
