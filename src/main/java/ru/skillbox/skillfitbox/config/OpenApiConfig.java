package ru.skillbox.skillfitbox.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SkillFitBox API")
                        .description("Fitness center management system API for managing clients, trainers, lockers, and services")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("SkillFitBox Team")
                                .email("support@skillfitbox.com")
                                .url("https://skillfitbox.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8090/api")
                                .description("Development server"),
                        new Server()
                                .url("https://api.skillfitbox.com")
                                .description("Production server")
                ));
    }
}
