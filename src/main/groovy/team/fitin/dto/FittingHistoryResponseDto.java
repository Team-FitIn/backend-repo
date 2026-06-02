package team.fitin.dto;

import lombok.Getter;
import team.fitin.domain.FittingHistory;
import java.time.LocalDateTime;

@Getter
public class FittingHistoryResponseDto {
    private final Long historyId;
    private final String garmentName;
    private final String garmentBrand;
    private final String garmentCategory;
    private final String resultImageUrl;
    private final LocalDateTime createdAt;

    public FittingHistoryResponseDto(FittingHistory history) {
        this.historyId = history.getId();
        this.garmentName = history.getGarment().getName(); // Garment 엔티티의 필드명에 맞게 조정하세요
        this.garmentBrand = history.getGarment().getBrand();
        this.garmentCategory = history.getGarment().getCategory();
        this.resultImageUrl = history.getResultImageUrl();
        this.createdAt = history.getCreatedAt();
    }
}
