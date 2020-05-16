package io.homo_efficio.monolith.simple_mall.dto;

import io.homo_efficio.monolith.simple_mall.domain.Seller;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SellerOut {

    private final String loginId;
    private final String name;
    private final String email;
    private final String phone;

    // Only for JacksonTester, DON'T use for production code
    public SellerOut() {
        this.loginId = null;
        this.name = null;
        this.email = null;
        this.phone = null;
    }

    public static SellerOut from(Seller seller) {
        return new SellerOut(
                seller.getLoginId(),
                seller.getName(),
                seller.getEmail(),
                seller.getPhone()
        );
    }
}
