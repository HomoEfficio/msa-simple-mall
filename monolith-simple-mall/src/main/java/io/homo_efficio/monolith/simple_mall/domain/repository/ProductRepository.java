package io.homo_efficio.monolith.simple_mall.domain.repository;

import io.homo_efficio.monolith.simple_mall.domain.Price;
import io.homo_efficio.monolith.simple_mall.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    List<Product> findAllByNameContaining(String name);
    List<Product> findAllByDescriptionContaining(String description);
    List<Product> findAllByManufacturerContaining(String manufacturer);
    List<Product> findAllByPriceGreaterThan(Price price);
    List<Product> findAllByPriceLessThan(Price price);
}
