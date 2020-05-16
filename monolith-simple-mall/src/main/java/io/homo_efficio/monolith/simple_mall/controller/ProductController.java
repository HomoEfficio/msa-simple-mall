package io.homo_efficio.monolith.simple_mall.controller;

import io.homo_efficio.monolith.simple_mall.dto.ProductIn;
import io.homo_efficio.monolith.simple_mall.dto.ProductOut;
import io.homo_efficio.monolith.simple_mall.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductOut> create(@RequestBody ProductIn productIn) {
        log.debug("Creating a product");
        return ResponseEntity.ok(productService.create(productIn));
    }
}
