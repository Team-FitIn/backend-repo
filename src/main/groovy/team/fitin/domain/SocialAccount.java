package team.fitin.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_account_id")
    private Long id;

    // 한 명의 회원은 카카오, 구글 등 여러 소셜 계정을 연결할 수 있음 (N:1 비식별)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 20)
    private String provider; // "KAKAO" 또는 "GOOGLE"

    @Column(nullable = false, length = 100)
    private String providerId; // 소셜 서비스에서 제공하는 고유 식별값 (ID)

    private String email; // 소셜 계정 이메일

    @Column(nullable = false)
    private LocalDateTime lastLoggedInAt;

    @Builder
    public SocialAccount(Member member, String provider, String providerId, String email) {
        this.member = member;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.lastLoggedInAt = LocalDateTime.now();
    }

    // 로그인할 때마다 시간을 업데이트하는 메서드
    public void updateLoginTime() {
        this.lastLoggedInAt = LocalDateTime.now();
    }
}
