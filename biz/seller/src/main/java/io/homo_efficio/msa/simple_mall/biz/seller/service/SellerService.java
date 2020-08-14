package io.homo_efficio.msa.simple_mall.biz.seller.service;

import io.homo_efficio.msa.simple_mall.biz.seller.dto.SellerIn;
import io.homo_efficio.msa.simple_mall.biz.seller.dto.SellerOut;
import reactor.core.publisher.Mono;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-07-06
 */
public interface SellerService {

    Mono<SellerOut> create(SellerIn sellerIn);
    Mono<SellerOut> update(String sellerId, SellerIn sellerIn);
    Mono<SellerOut> delete(String sellerId);

    Mono<SellerOut> findById(String sellerId);
}
