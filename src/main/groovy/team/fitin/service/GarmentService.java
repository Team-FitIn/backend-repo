package team.fitin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.fitin.domain.Garment;
import team.fitin.repository.GarmentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GarmentService {

    private final GarmentRepository garmentRepository;

    /**
     * 모든 의류 목록 조회
     */
    public List<Garment> findAllGarments() {
        return garmentRepository.findAll();
    }

    /**
     * 카테고리별 의류 조회
     */
    public List<Garment> findGarmentsByCategory(String category) {
        return garmentRepository.findByCategoryMain(category);
    }
}
