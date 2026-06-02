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
@Table(name = "fitting_history")
public class FittingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fitting_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garment_id", nullable = false)
    private Garment garment;

    @Column(nullable = false, length = 1000)
    private String resultImageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public FittingHistory(Member member, Garment garment, String resultImageUrl) {
        this.member = member;
        this.garment = garment;
        this.resultImageUrl = resultImageUrl;
        this.createdAt = LocalDateTime.now();
    }
}
