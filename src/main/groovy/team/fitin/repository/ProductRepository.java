package team.fitin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.fitin.domain.Product;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
