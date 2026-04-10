package team.fitin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.fitin.domain.Garment;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GarmentResponseDto {
    private Long id;
    private String name;
    private String brand;
    private String categoryMain;
    private String categorySub;
    private String imageUrl;
    private String originalLink;

    public static GarmentResponseDto from(Garment garment) {
        return GarmentResponseDto.builder()
                .id(garment.getId())
                .name(garment.getName())
                .brand(garment.getBrand())
                .categoryMain(garment.getCategoryMain())
                .categorySub(garment.getCategorySub())
                .imageUrl(garment.getImageUrl())
                .originalLink(garment.getOriginalLink())
                .build();
    }
}
