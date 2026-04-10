package team.fitin.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails { // 시큐리티 인터페이스 추가

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    // --- 보안을 위해 추가된 필드 ---
    @Column(nullable = false, unique = true)
    private String email; // 로그인 아이디로 사용

    @Column(nullable = false)
    private String password; // 암호화된 비밀번호 저장
    // ----------------------------

    @Column(length = 255)
    private String faceImageUrl;

    private Float height;
    private Float weight;
    private Float shoulderWidth;
    private Float legLength;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // --- UserDetails 구현 메서드 ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 기본 권한 설정 (일반 유저)
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.email; // 시큐리티가 사용자를 식별하는 값으로 email 사용
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
