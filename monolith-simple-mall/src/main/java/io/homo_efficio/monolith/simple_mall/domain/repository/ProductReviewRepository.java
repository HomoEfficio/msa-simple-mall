package io.homo_efficio.monolith.simple_mall.domain.repository;

import io.homo_efficio.monolith.simple_mall.domain.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-20
 */
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    Optional<ProductReview> findById(Long id);
    Optional<ProductReview> findByProduct_IdAndCustomer_Id(Long productId, Long customerId);

    List<ProductReview> findAllByProduct_Id(Long productId);
    List<ProductReview> findAllByCustomer_Id(Long customerId);
}
