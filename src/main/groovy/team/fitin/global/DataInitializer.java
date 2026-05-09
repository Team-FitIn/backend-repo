package team.fitin.global;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.fitin.domain.Product;
import team.fitin.dto.ProductJsonDto;
import team.fitin.repository.ProductRepository;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 1. JSON 파일 경로 지정
        ClassPathResource resource = new ClassPathResource("json/musinsa_tops.json");

        if (!resource.exists()) {
            log.info("초기 데이터 파일이 없어 스킵합니다.");
            return;
        }

        try (InputStream inputStream = resource.getInputStream()) {
            // 2. JSON 파싱
            List<ProductJsonDto> dtos = objectMapper.readValue(inputStream, new TypeReference<List<ProductJsonDto>>() {});

            // 3. DB 적재 (중복 제외)
            for (ProductJsonDto dto : dtos) {
                if (productRepository.findByName(dto.getName()).isEmpty()) {
                    Product product = Product.builder()
                            .name(dto.getName())
                            .imgUrl(dto.getImgUrl())
                            .link(dto.getLink())
                            .build();
                    productRepository.save(product);
                }
            }
            log.info("무신사 데이터 {}건이 DB에 적재되었습니다.", dtos.size());
        } catch (Exception e) {
            log.error("데이터 적재 중 오류 발생: ", e);
        }
    }
}
