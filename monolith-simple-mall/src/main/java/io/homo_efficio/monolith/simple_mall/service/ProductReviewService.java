package io.homo_efficio.monolith.simple_mall.service;


import io.homo_efficio.monolith.simple_mall.dto.ProductReviewIn;
import io.homo_efficio.monolith.simple_mall.dto.ProductReviewOut;

import java.util.List;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-20
 */
public interface ProductReviewService {

    ProductReviewOut create(ProductReviewIn productReviewIn);

    ProductReviewOut update(Long id, ProductReviewIn productReviewIn);

    Long delete(Long id);

    ProductReviewOut findById(Long id);

    List<ProductReviewOut> findAllByProductId(Long productId);
    List<ProductReviewOut> findAllByCustomerId(Long customerId);
}
