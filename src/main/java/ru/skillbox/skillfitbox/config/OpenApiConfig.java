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
                        .description("Приложение представляет собой систему управления фитнес-центром с возможностью управления клиентами, тренерами, шкафчиками и дополнительными услугами.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("SkillFitBox Team")
                                .email("support@skillbox.ru")
                                .url("https://skillbox.ru"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8090/")
                                .description("Development server"),
                        new Server()
                                .url("https://api.skillbox.ru")
                                .description("Production server")
                ));
    }
}
