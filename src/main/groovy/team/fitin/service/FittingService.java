package team.fitin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FittingService {

    private final WebClient aiServerWebClient;

    /**
     * IDM-VTON AI 서버에 가상 피팅 합성을 요청합니다.
     */
    public byte[] requestVirtualFitting(MultipartFile userFaceImage, String garmentImageUrl, String category) {

        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();

        bodyBuilder.part("user_face", userFaceImage.getResource());
        bodyBuilder.part("garment_url", garmentImageUrl);
        bodyBuilder.part("category", category);

        try {
            return aiServerWebClient.post()
                    .uri("/v1/models/idm-vton")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .block();

        } catch (Exception e) {
            System.err.println("❌ [WebClient 연동 에러] FastAPI 서버 합성 실패: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("AI 서브 서버 연동 중 오류가 발생했습니다.", e);
        }
    }
}
