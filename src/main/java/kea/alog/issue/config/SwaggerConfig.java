package kea.alog.issue.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openAPI(){
        Info info = new Info()
        .title("Alog Issue API")
        .description("Alog Issue API")
        .version("1.0.0");

        return new OpenAPI()
        .components(new Components())
        .info(info);
    }

    @Bean
    public GroupedOpenApi releaseGroup(){
        return GroupedOpenApi.builder()
        .group("post")
        .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Issue API").version("1.0.0").description("Issue API")))
        .build();
    }
}