package io.homo_efficio.monolith.simple_mall.dto;

import io.homo_efficio.monolith.simple_mall.domain.Address;
import io.homo_efficio.monolith.simple_mall.domain.Customer;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-19
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerIn {

    @NotEmpty
    @Size(min = 1, max = 90, message = "이름은 최소 1자, 최대 30자 입니다.")
    private String name;

    @NotEmpty
    @Email
    @Size(min = 6, max = 50, message = "email은 최소 6자, 최대 50자 입니다.")
    private String email;

    @NotEmpty
    @Size(min = 9, max = 20, message = "전화번호는 최소 9자, 최대 20자 입니다.")
    private String phone;

    private Address address;

    @NotEmpty
    @Size(min = 5, max = 30, message = "로그인 ID는 최소 5자, 최대 30자 입니다.")
    private String loginId;

    @NotEmpty
    @Size(min = 6, max = 20, message = "비밀번호는 최소 6자, 최대 20자 입니다.")
    private String password;


    public Customer toEntityWithPasswordEncoder(PasswordEncoder passwordEncoder) {
        return new Customer(null, name, email, phone, address, loginId, passwordEncoder.encode(password));
    }

    public void updateEntityWithPasswordEncoder(Customer customer, PasswordEncoder passwordEncoder) {
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(passwordEncoder.encode(password));
        customer.setPhone(phone);
        customer.setAddress(address);
    }
}
