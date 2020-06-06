package io.homo_efficio.monolith.simple_mall.controller;

import io.homo_efficio.monolith.simple_mall._common.exception.EntityNotFoundException;
import io.homo_efficio.monolith.simple_mall.domain.Customer;
import io.homo_efficio.monolith.simple_mall.domain.repository.CustomerRepository;
import io.homo_efficio.monolith.simple_mall.dto.CustomerIn;
import io.homo_efficio.monolith.simple_mall.dto.CustomerOut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-19
 */
@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
@Transactional
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<CustomerOut> create(@RequestBody CustomerIn customerIn) {
        return ResponseEntity.ok(CustomerOut.from(customerRepository.save(customerIn.toEntityWithPasswordEncoder(passwordEncoder))));
    }

    @GetMapping
    public ResponseEntity<CustomerOut> findByLoginId(@RequestParam("loginId") String loginId) {
        return ResponseEntity.ok(CustomerOut.from(
                        customerRepository.findByLoginId(loginId)
                                .orElseThrow(() -> new EntityNotFoundException(Customer.class,
                                        String.format("판매자 [%s] 는 존재하지 않습니다.", loginId)))));
    }
}
