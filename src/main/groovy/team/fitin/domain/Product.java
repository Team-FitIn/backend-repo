package team.fitin.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(length = 1000) // 무신사 이미지 URL이 길어 잘리는 현상 방지
    private String imgUrl;

    private String link;

    @Builder
    public Product(String name, String imgUrl, String link) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.link = link;
    }
}
