package team.fitin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BodyInfoRequestDto {

    @Schema(description = "사용자 키", example = "180.5")
    private Float height;

    @Schema(description = "사용자 몸무게", example = "75.0")
    private Float weight;

    @Schema(description = "어깨 너비", example = "45.2")
    private Float shoulderWidth;

    @Schema(description = "다리 길이", example = "105.0")
    private Float legLength;
}
