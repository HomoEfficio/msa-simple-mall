package io.homo_efficio.msa.simple_mall.biz.seller.service;

import io.homo_efficio.msa.simple_mall.biz.seller.dto.SellerIn;
import io.homo_efficio.msa.simple_mall.biz.seller.dto.SellerOut;
import reactor.core.publisher.Mono;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-07-06
 */
public interface SellerService {

    Mono<SellerOut> create(Mono<SellerIn> sellerIn);
    Mono<SellerOut> update(String sellerId, Mono<SellerIn> sellerIn);
}
