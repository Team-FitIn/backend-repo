package team.fitin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.fitin.dto.GarmentResponseDto;
import team.fitin.repository.GarmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GarmentService {

    private final GarmentRepository garmentRepository;

    public List<GarmentResponseDto> findAllGarments() {
        return garmentRepository.findAll().stream()
                .map(GarmentResponseDto::from)
                .collect(Collectors.toList());
    }

    public List<GarmentResponseDto> findGarmentsByCategory(String category) {
        return garmentRepository.findByCategoryMain(category).stream()
                .map(GarmentResponseDto::from)
                .collect(Collectors.toList());
    }

    public List<GarmentResponseDto> findGarmentsByBrand(String brand) {
        return garmentRepository.findByBrand(brand).stream()
                .map(GarmentResponseDto::from)
                .collect(Collectors.toList());
    }
}
