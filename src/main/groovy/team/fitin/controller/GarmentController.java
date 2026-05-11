package team.fitin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.fitin.dto.GarmentResponseDto;
import team.fitin.service.GarmentService;

import java.util.List;

@Tag(name = "Garment", description = "의류 관련 API") // API 그룹 제목
@RestController
@RequestMapping("/api/garments")
@RequiredArgsConstructor
public class GarmentController {

    private final GarmentService garmentService;

    @Operation(summary = "전체 의류 조회", description = "DB에 저장된 모든 무신사 의류 목록을 가져옵니다.")
    @GetMapping
    public List<GarmentResponseDto> getAllGarments() {
        return garmentService.findAllGarments();
    }

    @Operation(summary = "카테고리별 의류 조회", description = "상의, 하의 등 특정 카테고리에 해당하는 의류 목록을 필터링합니다.")
    @GetMapping("/category/{category}")
    public List<GarmentResponseDto> getGarmentsByCategory(
            @PathVariable("category") String category
    ) {
        return garmentService.findGarmentsByCategory(category);
    }

    @Operation(summary = "브랜드별 의류 조회", description = "특정 브랜드의 의류 목록을 필터링합니다. 결과에는 무신사 구매 페이지 링크가 포함됩니다.")
    @GetMapping("/brand/{brand}")
    public List<GarmentResponseDto> getGarmentsByBrand(
            @PathVariable("brand") String brand // ("brand") 명시!
    ) {
        return garmentService.findGarmentsByBrand(brand);
    }
}
