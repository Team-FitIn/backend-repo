package team.fitin.global;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import team.fitin.domain.Garment;
import team.fitin.repository.GarmentRepository;

@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {

    private final GarmentRepository garmentRepository;

    @Override
    public void run(String... args) throws Exception {
        // 이미 데이터가 있다면 중복으로 넣지 않기 위해 체크
        if (garmentRepository.count() == 0) {

            // 1. 상의 샘플
            garmentRepository.save(Garment.builder()
                    .name("오버사이즈 반팔 티셔츠")
                    .brand("무신사 스탠다드")
                    .categoryMain("TOP")
                    .categorySub("Short Sleeve")
                    .imageUrl("https://image.musinsa.com/sample_top.jpg")
                    .originalLink("https://www.musinsa.com/app/goods/12345")
                    .build());

            // 2. 하의 샘플
            garmentRepository.save(Garment.builder()
                    .name("와이드 데님 팬츠")
                    .brand("리바이스")
                    .categoryMain("BOTTOM")
                    .categorySub("Denim")
                    .imageUrl("https://image.musinsa.com/sample_pants.jpg")
                    .originalLink("https://www.musinsa.com/app/goods/67890")
                    .build());

            System.out.println(">> [Success] 테스트용 무신사 옷 데이터 삽입 완료!");
        }
    }
}
