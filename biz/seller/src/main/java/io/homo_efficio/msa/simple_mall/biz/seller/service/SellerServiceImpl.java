package io.homo_efficio.msa.simple_mall.biz.seller.service;

import io.homo_efficio.biz.common.exception.ResourceNotFoundException;
import io.homo_efficio.msa.simple_mall.biz.seller.domain.model.Seller;
import io.homo_efficio.msa.simple_mall.biz.seller.domain.repository.SellerRepository;
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
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<SellerOut> create(SellerIn sellerIn) {
        return SellerOut.from(
                sellerRepository.save(sellerIn.toEntityWithPasswordEncoder(passwordEncoder))
        );
    }


    @Override
    public Mono<SellerOut> update(String sellerId, SellerIn sellerIn) {
        log.info(Thread.currentThread().getName() + " in update() - 1");
        Mono<Seller> sellerMono = sellerRepository.findById(sellerId)
                .switchIfEmpty(Mono.error(() -> {
                    log.info(Thread.currentThread().getName() + " in update() - 2");
                    return new ResourceNotFoundException(Seller.class, sellerId);
                }))
                .map(updateEntityWith(sellerIn));

        return SellerOut.from(template.save(sellerMono));
    }

    private Function<Seller, ? extends Seller> updateEntityWith(SellerIn sellerIn) {
        log.info(Thread.currentThread().getName() + " in updateEntityWith() - 3");
        return seller -> {
        log.info(Thread.currentThread().getName() + " in updateEntityWith() - 4");
            seller.setName(sellerIn.getName());
            seller.setEmail(sellerIn.getEmail());
            seller.setPhone(sellerIn.getPhone());
            seller.setPassword(sellerIn.getPassword());
            return seller;
        };
    }

    @Override
    public Mono<SellerOut> delete(String sellerId) {
        return SellerOut.from(
                sellerRepository.findById(sellerId)
                        .switchIfEmpty(Mono.error(() -> new ResourceNotFoundException(Seller.class, sellerId)))
                        .doOnNext(template::remove));
    }
}
