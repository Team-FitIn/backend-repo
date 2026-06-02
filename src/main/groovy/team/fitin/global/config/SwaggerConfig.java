package team.fitin.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String securitySchemeName = "BearerAuth";

        Components components = new Components()
                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("일반 로그인 API(/api/auth/login)에서 발급받은 'accessToken'을 입력하세요."));

        return new OpenAPI()
                .info(new Info()
                        .title("Fit-In API 명세서")
                        .description("AI 가상 피팅 서비스 Fit-In의 백엔드 API 문서입니다.")
                        .version("1.0.0"))
                .components(components);
    }
}
