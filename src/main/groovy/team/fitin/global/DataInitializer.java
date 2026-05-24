package team.fitin.global;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.fitin.domain.Garment;
import team.fitin.dto.GarmentJsonDto;
import team.fitin.repository.GarmentRepository;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final GarmentRepository garmentRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // JSON 파일 경로 지정
        ClassPathResource resource = new ClassPathResource("json/musinsa_tops.json");

        if (!resource.exists()) {
            log.info("초기 데이터 파일이 없어 스킵합니다.");
            return;
        }

        try (InputStream inputStream = resource.getInputStream()) {
            // 2. JSON 파싱 (기존 DTO 구조 활용)
            List<GarmentJsonDto> dtos = objectMapper.readValue(inputStream, new TypeReference<List<GarmentJsonDto>>() {});

            // 3. DB 적재 및 기존 가짜 데이터 정밀 동기화
            for (GarmentJsonDto dto : dtos) {
                Optional<Garment> existingGarment = garmentRepository.findByName(dto.getName());

                if (existingGarment.isEmpty()) {
                    // [Case 1] DB에 없는 완전히 새로운 옷이라면 새롭게 객체 생성 후 저장
                    Garment garment = Garment.builder()
                            .name(dto.getName())
                            .brand("무신사(Musinsa)") // JSON에 브랜드 정보가 없을 경우 기본값 설정
                            .categoryMain("상의")     // 현재 파일(tops)에 맞춰 기본값 설정
                            .categorySub("티셔츠")    // 필요 시 상세 분류 로직 추가 가능
                            .imageUrl(dto.getImgUrl()) // 누끼 이미지 매핑
                            .originalLink(dto.getLink()) // 무신사 원본 구매 링크 매핑
                            .build();

                    garmentRepository.save(garment);
                } else {
                    Garment targetGarment = existingGarment.get();

                    if (!targetGarment.getImageUrl().equals(dto.getImgUrl())) {
                        targetGarment.updateCrawlData(dto.getImgUrl(), dto.getLink());
                        log.info("🔄 기존 가짜 데이터를 최신 크롤링 주소로 동기화 완료: {}", dto.getName());
                    }
                }
            }
            log.info("무신사 크롤링 데이터 총 {}건이 DB와 100% 정밀 동기화되었습니다.", dtos.size());
        } catch (Exception e) {
            log.error("데이터 적재 중 오류 발생: ", e);
        }
    }
}
