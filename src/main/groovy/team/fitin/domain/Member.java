package team.fitin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 255)
    private String faceImageUrl; // 실제 AI 합성에 사용될 얼굴 이미지 URL

    private Float height;
    private Float weight;
    private Float shoulderWidth;
    private Float legLength;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 데이터 저장 전 자동으로 시간을 넣어주는 메서드
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
