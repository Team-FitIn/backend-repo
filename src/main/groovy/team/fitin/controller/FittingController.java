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
@RequestMapping("/api/fitting")
@RequiredArgsConstructor
public class FittingController {

    private final FittingService fittingService;

    @Operation(
            summary = "마네킹 기반 가상 피팅 수행",
            description = "유저의 [얼굴 사진]과 [의류 ID]를 받아, 내부 마네킹 모델에 얼굴과 옷을 합성한 최종 PNG 이미지 파일을 반환합니다."
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> tryOn(
            @RequestPart("userFaceImage") @Parameter(description = "유저 얼굴 중심의 정면 사진 파일") MultipartFile userFaceImage,
            @RequestParam("garmentId") @Parameter(description = "입혀볼 무신사 의류 고유 ID") Long garmentId
    ) {
        byte[] mixedImage = fittingService.requestVirtualFitting(userFaceImage, garmentId);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(mixedImage);
    }
}
