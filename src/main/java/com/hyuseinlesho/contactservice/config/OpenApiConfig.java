package com.hyuseinlesho.contactservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                description = "ContactService is a RESTful service designed to handle the creation and retrieval of contact messages. ",
                title = "ContactService API",
                version = "1.0"
        )
)
@Configuration
public class OpenApiConfig {
        @Bean
        public GroupedOpenApi publicApi() {
                return GroupedOpenApi.builder()
                        .group("contacts")
                        .pathsToMatch("/api/contacts/**")
                        .build();
        }
}
