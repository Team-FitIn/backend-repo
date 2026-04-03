package team.fitin.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Garment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "garment_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name; // 상품명

    private String brand; // 브랜드

    @Column(nullable = false)
    private String categoryMain; // 대분류 (상의, 하의 등)

    private String categorySub; // 소분류 (반팔 티셔츠 등)

    @Column(nullable = false, length = 500)
    private String imageUrl; // 배경 제거된 옷 이미지 URL

    private String originalLink; // 무신사 원본 링크

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
