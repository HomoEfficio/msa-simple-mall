package io.homo_efficio.msa.simple_mall.biz.seller.service;

import io.homo_efficio.msa.simple_mall.biz.seller.dto.SellerIn;
import io.homo_efficio.msa.simple_mall.biz.seller.dto.SellerOut;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-07-06
 */
@Service
@RequiredArgsConstructor
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
}
