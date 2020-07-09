package io.homo_efficio.msa.simple_mall.biz.seller.controller;

import io.homo_efficio.msa.simple_mall.biz.seller.dto.SellerIn;
import io.homo_efficio.msa.simple_mall.biz.seller.dto.SellerOut;
import io.homo_efficio.msa.simple_mall.biz.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-07-06
 */
@RestController
@RequestMapping("/sellers")
@RequiredArgsConstructor
@Slf4j
public class SellerController {

    private final SellerService sellerService;

    @PostMapping
    public Mono<SellerOut> create(@Valid @RequestBody Mono<SellerIn> sellerIn) {
        return sellerService.create(sellerIn);
    }
}
