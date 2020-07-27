package io.homo_efficio.msa.simple_mall.biz.seller.service;

import io.homo_efficio.biz.common.exception.ResourceNotFoundException;
import io.homo_efficio.msa.simple_mall.biz.seller.domain.model.Seller;
import io.homo_efficio.msa.simple_mall.biz.seller.dto.SellerIn;
import io.homo_efficio.msa.simple_mall.biz.seller.dto.SellerOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-07-06
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements SellerService {

    private final ReactiveMongoTemplate template;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<SellerOut> create(Mono<SellerIn> sellerIn) {
        return SellerOut.from(
                template.save(
                        sellerIn.map(s -> s.toEntityWithPasswordEncoder(passwordEncoder))
                )
        );
    }

    @Override
    public Mono<SellerOut> update(String sellerId, Mono<SellerIn> sellerIn) {
//        log.info(Thread.currentThread().getName() + " in update() - 1");
        Mono<Seller> sellerMono = template.findById(sellerId, Seller.class)
//                .switchIfEmpty(Mono.error(() -> new ResourceNotFoundException(Seller.class, sellerId)))
                .switchIfEmpty(Mono.error(() -> {
//                    log.info(Thread.currentThread().getName() + " in update() - 2");
                    return new ResourceNotFoundException(Seller.class, sellerId);
                }))
                .flatMap(updateEntityWith(sellerIn));

        return SellerOut.from(template.save(sellerMono));
    }

    private Function<Seller, Mono<? extends Seller>> updateEntityWith(Mono<SellerIn> sellerIn) {
//        log.info(Thread.currentThread().getName() + " in updateEntityWith() - 3");
        return seller -> sellerIn.map(s -> {
//            log.info(Thread.currentThread().getName() + " in updateEntityWith() - 4");
            s.updateEntityWithPasswordEncoder(seller, passwordEncoder);
            return seller;
        });
    }

    @Override
    public Mono<SellerOut> delete(String sellerId) {

        return SellerOut.from(
                template.findById(sellerId, Seller.class)
                        .switchIfEmpty(Mono.error(() -> new ResourceNotFoundException(Seller.class, sellerId)))
                        .doOnNext(template::remove));
    }
}
