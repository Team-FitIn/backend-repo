package team.fitin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.fitin.service.FittingService;

@Tag(name = "Fitting", description = "마네킹 및 IDM-VTON 기반 가상 피팅 API")
@RestController
@RequestMapping("/api/v1/fitting") // 1. 외부 연동 가이드와 일치하도록 버전(v1) 명시
@RequiredArgsConstructor
public class FittingController {

    private final FittingService fittingService;

    @Operation(
            summary = "마네킹 기반 가상 피팅 수행",
            description = "유저의 [얼굴 사진]과 무신사에서 가져온 [의류 이미지 파일], [카테고리]를 받아 딥러닝 합성한 최종 PNG 이미지 파일을 반환합니다."
    )
    @PostMapping(value = "/try-on", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> tryOn(
            @RequestPart("user_face") @Parameter(description = "유저 얼굴 중심의 정면 사진 파일") MultipartFile userFace,
            @RequestPart("garment_file") @Parameter(description = "입혀볼 실제 의류 이미지 파일") MultipartFile garmentFile,
            @RequestParam("category") @Parameter(description = "의류 카테고리 (TOP, BOTTOM 등)") String category
    ) {
        byte[] mixedImage = fittingService.requestVirtualFitting(userFace, garmentFile, category);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(mixedImage);
    }
}
