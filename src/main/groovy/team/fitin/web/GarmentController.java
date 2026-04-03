package team.fitin.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.fitin.domain.Garment;
import team.fitin.service.GarmentService;

import java.util.List;

@RestController
@RequestMapping("/api/garments")
@RequiredArgsConstructor
public class GarmentController {

    private final GarmentService garmentService;

    @GetMapping
    public List<Garment> getAllGarments() {
        return garmentService.findAllGarments();
    }

    @GetMapping("/category/{category}")
    public List<Garment> getGarmentsByCategory(@PathVariable String category) {
        return garmentService.findGarmentsByCategory(category);
    }
}
