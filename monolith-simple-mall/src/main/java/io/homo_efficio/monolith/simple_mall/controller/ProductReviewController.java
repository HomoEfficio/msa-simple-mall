package io.homo_efficio.monolith.simple_mall.controller;

import io.homo_efficio.monolith.simple_mall.dto.ProductReviewIn;
import io.homo_efficio.monolith.simple_mall.dto.ProductReviewOut;
import io.homo_efficio.monolith.simple_mall.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Page<ProductReviewOut>> findAllByCustomerId(@RequestParam("customer") Long customerId,
                                                                      @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(productReviewService.findAllByCustomerId(customerId, pageable));
    }
}
