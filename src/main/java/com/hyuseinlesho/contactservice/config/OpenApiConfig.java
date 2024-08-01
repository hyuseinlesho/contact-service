package com.hyuseinlesho.contactservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                description = "ContactService is a RESTful service designed to handle the creation and retrieval of contact messages. " +
                        "This service is used in conjunction with the PowerLog application to manage user contacts efficiently.",
                title = "ContactService API",
                version = "1.0"
        )
)
public class OpenApiConfig {
}
