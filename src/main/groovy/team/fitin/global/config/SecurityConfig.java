package team.fitin.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import team.fitin.global.security.CustomOAuth2UserService;
import team.fitin.global.security.JwtAuthenticationFilter;
import team.fitin.global.security.JwtTokenProvider;
import team.fitin.global.security.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService; // 추가
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler; // 추가

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 기본 보안 설정 비활성화 (Stateless 방식)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 2. 경로별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // Swagger 및 공통 리소스 허용
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/webjars/**",
                                "/swagger-resources/**",
                                "/favicon.ico",
                                "/error"
                        ).permitAll()
                        // 인증 관련 및 소셜 로그인 관련 경로 허용
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/garments/**",
                                "/login/oauth2/**",
                                "/oauth2/**",
                                "/api/members/**"
                        ).permitAll()
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )

                // 3. OAuth2 소셜 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        // 소셜 서비스로부터 유저 정보를 가져오는 서비스 설정
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        // 인증 성공 시 JWT 발급 로직을 담은 핸들러 설정
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                )

                // 4. JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 배치
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
