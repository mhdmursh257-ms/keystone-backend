package com.key_stone.Config;

import org.springframework.context.annotation.*;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		final String securitySchemeName = "bearerAuth";
		return new OpenAPI()
				.info(new Info()
						.title("KEYSTONE Field Service Management API")
						.version("1.0")
						.description("REST API Documentation with JWT Security"))
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(new Components()
						.addSecuritySchemes(securitySchemeName, 
								new SecurityScheme()
									.name(securitySchemeName)
									.type(SecurityScheme.Type.HTTP)
									.scheme("bearer")
									.bearerFormat("JWT")));
	}
	
}
