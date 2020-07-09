package io.homo_efficio.msa.simple_mall.biz.seller.dto;

import io.homo_efficio.msa.simple_mall.biz.seller.domain.model.Seller;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SellerOut {

    private final String id;
    private final String loginId;
    private final String name;
    private final String email;
    private final String phone;

    // Only for JacksonTester, DON'T use for production code
    public SellerOut() {
        this.id = null;
        this.loginId = null;
        this.name = null;
        this.email = null;
        this.phone = null;
    }

    public static Mono<SellerOut> from(Mono<Seller> seller) {
        return seller.map(s -> new SellerOut(
                s.getId(),
                s.getLoginId(),
                s.getName(),
                s.getEmail(),
                s.getPhone()
        ));
    }
}
