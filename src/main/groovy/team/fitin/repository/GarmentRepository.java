package team.fitin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.fitin.domain.Garment;

import java.util.List;
import java.util.Optional;

@Repository
public interface GarmentRepository extends JpaRepository<Garment, Long> {

    // 특정 카테고리(상의, 하의 등)의 옷들만 모아보기
    List<Garment> findByCategoryMain(String categoryMain);

    Optional<Garment> findByName(String name);

    // 브랜드 이름으로 검색하기
    List<Garment> findByBrand(String brand);

    // 상품명 또는 브랜드에 키워드 포함 여부로 검색 (대소문자 무시)
    List<Garment> findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(String name, String brand);
}
