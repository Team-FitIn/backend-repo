package team.fitin.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fit-In API 명세서")
                        .description("AI 가상 피팅 서비스 Fit-In의 백엔드 API 문서입니다.")
                        .version("1.0.0"));
    }
}
