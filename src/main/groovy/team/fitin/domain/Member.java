package team.fitin.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 무분별한 객체 생성 방지
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // --- 신체 및 프로필 정보 ---
    private String faceImageUrl;
    private Float height;
    private Float weight;
    private Float shoulderWidth;
    private Float legLength;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 엔티티가 처음 저장될 때 생성 시간을 자동으로 기록합니다.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // --- Spring Security UserDetails 구현 메서드 ---

    /**
     * 계정이 가진 권한 목록을 반환합니다.
     * 현재는 모든 유저에게 'ROLE_USER' 권한을 기본 부여합니다.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 사용자의 신체 정보를 업데이트합니다.
     */
    public void updateBodyInfo(Float height, Float weight, Float shoulderWidth, Float legLength) {
        this.height = height;
        this.weight = weight;
        this.shoulderWidth = shoulderWidth;
        this.legLength = legLength;
    }
}
