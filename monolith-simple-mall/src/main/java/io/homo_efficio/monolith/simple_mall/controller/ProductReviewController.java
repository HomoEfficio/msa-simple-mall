package io.homo_efficio.monolith.simple_mall.controller;

import io.homo_efficio.monolith.simple_mall.dto.ProductReviewIn;
import io.homo_efficio.monolith.simple_mall.dto.ProductReviewOut;
import io.homo_efficio.monolith.simple_mall.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-23
 */
@RestController
@RequestMapping("/v1/product-reviews")
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    @PostMapping
    public ResponseEntity<ProductReviewOut> create(@RequestBody ProductReviewIn productReviewIn) {
        return ResponseEntity.ok(productReviewService.create(productReviewIn));
    }
}
