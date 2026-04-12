package team.fitin.global.security.dto;

import lombok.Builder;
import lombok.Getter;
import team.fitin.domain.Member;

import java.util.Map;

/**
 * 소셜 서비스별로 다른 응답 데이터를 하나로 통일하는 DTO
 */
@Getter
@Builder
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("kakao".equals(registrationId)) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        return OAuthAttributes.builder()
                .email((String) kakaoAccount.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /**
     * 처음 가입 시 Member 엔티티를 생성합니다.
     * 신체 정보는 소셜에서 주지 않으므로 기본값(null)으로 설정합니다.
     */
    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password("") // 소셜 로그인은 비밀번호가 필요 없음
                .build();
    }
}
