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

    @Column(nullable = false, length = 255)
    private String name; // 상품명

    private String brand; // 브랜드

    @Column(nullable = false)
    private String categoryMain; // 대분류 (상의, 하의 등)

    private String categorySub; // 소분류 (반팔 티셔츠 등)

    @Column(nullable = false, length = 1000)
    private String imageUrl; // 배경 제거된 옷 이미지 URL

    @Column(length = 1000)
    private String originalLink; // 무신사 원본 링크

    @Column(nullable = false)
    private LocalDateTime createdAt;


    // 기존 데이터를 최신 클롤링 데이터로 변경
    public void updateCrawlData(String imageUrl, String originalLink) {
        this.imageUrl = imageUrl;
        this.originalLink = originalLink;
    }

    public String getCategory() {
        return this.categoryMain;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
