package io.homo_efficio.monolith.simple_mall.dto;

import io.homo_efficio.monolith.simple_mall.domain.Address;
import io.homo_efficio.monolith.simple_mall.domain.Customer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-19
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CustomerOut {

    private final String loginId;
    private final String name;
    private final String email;
    private final String phone;
    private final Address address;

    // Only for JacksonTester, DON'T use for production code
    public CustomerOut() {
        this.loginId = null;
        this.name = null;
        this.email = null;
        this.phone = null;
        this.address = null;
    }

    public static CustomerOut from(Customer customer) {
        return new CustomerOut(
                customer.getLoginId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress()
        );
    }
}
