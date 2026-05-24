package team.fitin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import team.fitin.domain.Garment;
import team.fitin.repository.GarmentRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FittingService {

    private final WebClient aiServerWebClient;
    private final GarmentRepository garmentRepository;

    /**
     * IDM-VTON AI 서버에 가상 피팅 합성을 요청합니다.
     * 어떤 garmentId가 들어와도 DB에서 실시간으로 진짜 무신사 URL을 꺼내와 동적으로 작동합니다.
     */
    public byte[] requestVirtualFitting(MultipartFile userFaceImage, Long garmentId) {
        // 1. 어떤 옷 ID가 들어와도 동적으로 DB에서 조회 (완벽한 자동화)
        Garment garment = garmentRepository.findById(garmentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 의류 ID입니다."));

        // 2. 해당 옷의 진짜 무신사 이미지 주소를 실시간 크롤링(다운로드)
        byte[] garmentImageBytes = downloadGarmentImage(garment.getImageUrl());

        // 3. 멀티파트 데이터 구성
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("user_face", userFaceImage.getResource());

        bodyBuilder.part("garment_file", new ByteArrayResource(garmentImageBytes))
                .filename("garment_product.jpg")
                .contentType(MediaType.IMAGE_JPEG);

        bodyBuilder.part("category", garment.getCategoryMain());

        // 4. 파이썬 AI 서버로 전송
        return aiServerWebClient.post()
                .uri("/api/v1/try-on")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(bodyBuilder.build())
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }

    /**
     * 무신사 이미지 서버의 차단 정책(봇 방지)을 우회하여 안전하게 이미지를 바이트 배열로 다운로드합니다.
     */
    private byte[] downloadGarmentImage(String imageUrl) {
        try {
            return WebClient.builder()
                    .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(20 * 1024 * 1024))
                    .defaultHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .build()
                    .get()
                    .uri(imageUrl)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .block();
        } catch (Exception e) {
            System.out.println("❌ 자바 단에서 무신사 이미지 다운로드 중 에러 발생: " + e.getMessage());
            throw new IllegalStateException("무신사 의류 이미지를 가져오는 데 실패했습니다. URL: " + imageUrl, e);
        }
    }
}
