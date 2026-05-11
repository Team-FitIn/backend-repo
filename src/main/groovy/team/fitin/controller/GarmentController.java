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

    @Operation(summary = "카테고리별 조회", description = "상의(TOP), 하의(BOTTOM) 등 카테고리별로 필터링합니다.")
    @GetMapping("/category/{category}")
    public List<GarmentResponseDto> getGarmentsByCategory(@PathVariable String category) {
        return garmentService.findGarmentsByCategory(category);
    }

    @Operation(summary = "브랜드별 조회", description = "특정 브랜드의 의류 목록만 필터링하여 가져옵니다.")
    @GetMapping("/brand/{brand}")
    public List<GarmentResponseDto> getGarmentsByBrand(@PathVariable String brand) {
        return garmentService.findGarmentsByBrand(brand);
    }
}
