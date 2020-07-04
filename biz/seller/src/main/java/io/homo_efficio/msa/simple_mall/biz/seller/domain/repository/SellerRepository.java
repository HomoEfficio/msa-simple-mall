package io.homo_efficio.msa.simple_mall.biz.seller.domain.repository;

import io.homo_efficio.msa.simple_mall.biz.seller.domain.model.Seller;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.math.BigInteger;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-06-29
 */
public interface SellerRepository extends ReactiveMongoRepository<Seller, BigInteger> {
}
