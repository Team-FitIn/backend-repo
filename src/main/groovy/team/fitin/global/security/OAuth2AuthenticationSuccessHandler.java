package team.fitin.global.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collections;

/**
 * 소셜 로그인 성공 시 실행되는 핸들러입니다.
 * 인증 정보를 바탕으로 JWT 토큰을 생성하여 리다이렉트
 */
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        // 1. JWT 토큰 생성
        String token = jwtTokenProvider.createToken(email, Collections.singletonList("ROLE_USER"));

        // 2. 프론트엔드(혹은 포스트맨)로 토큰을 전달할 URL 생성
        // 실제 서비스에서는 프론트엔드의 특정 페이지로 토큰을 쿼리 파라미터로 보냅니다.
        String targetUrl = UriComponentsBuilder.fromUriString("/api/auth/social-success")
                .queryParam("token", token)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
