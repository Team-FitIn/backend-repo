package team.fitin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.fitin.domain.Garment;
import team.fitin.domain.FittingHistory;
import team.fitin.domain.Member;
import team.fitin.repository.GarmentRepository;
import team.fitin.repository.MemberRepository;
import team.fitin.repository.FittingHistoryRepository;
import team.fitin.service.FittingService;

@Tag(name = "Fitting", description = "마네킹 및 IDM-VTON 기반 가상 피팅 API")
@RestController
@RequestMapping("/api/v1/fitting")
public class FittingController {

    private final FittingService fittingService;
    private final GarmentRepository garmentRepository;
    private final MemberRepository memberRepository;
    private final FittingHistoryRepository fittingHistoryRepository;

    public FittingController(FittingService fittingService,
                             GarmentRepository garmentRepository,
                             MemberRepository memberRepository,
                             FittingHistoryRepository fittingHistoryRepository) {
        this.fittingService = fittingService;
        this.garmentRepository = garmentRepository;
        this.memberRepository = memberRepository;
        this.fittingHistoryRepository = fittingHistoryRepository;
    }

    @Operation(
            summary = "마네킹 기반 가상 피팅 수행 및 이력 저장",
            description = "유저의 [얼굴 사진]과 무신사 [의류 ID]를 받아 딥러닝 합성 후, 결과를 DB에 저장하고 PNG 파일을 반환합니다."
    )
    @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "BearerAuth")
    @PostMapping(value = "/try-on/{garmentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> tryOn(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("garmentId") Long garmentId,
            @RequestPart("user_face") @Parameter(description = "유저 얼굴 중심의 정면 사진 파일", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(type = "string", format = "binary"))) MultipartFile userFace
    ) {

        if (userDetails == null) {
            throw new RuntimeException("인증 정보가 없습니다. 다시 로그인해 주세요.");
        }
        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        getGarmentId(garmentId);

        Garment garment = garmentRepository.findById(garmentId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 의류 정보입니다."));

        // 3. 의류 정보로부터 텍스트 파일(누끼 이미지 등) 추출 또는 가공 후 AI 서버 호출
        byte[] mixedImage = fittingService.requestVirtualFitting(userFace, garment.getImageUrl(), garment.getCategory());

        // 임시 테스트용 가짜 S3 URL 구조
        String temporaryS3Url = "https://fit-in-s3-bucket.s3.ap-northeast-2.amazonaws.com/results/"
                + member.getId() + "_" + garmentId + "_" + System.currentTimeMillis() + ".png";

        // 피팅 히스토리 이력 빌드 및 적재
        FittingHistory history = FittingHistory.builder()
                .member(member)
                .garment(garment)
                .resultImageUrl(temporaryS3Url)
                .build();

        fittingHistoryRepository.save(history);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(mixedImage);
    }

    private static void getGarmentId(Long garmentId) {
        System.out.println("👉 [Try-On 요청 감지] Target Garment ID: " + garmentId);
    }
}
