package io.homo_efficio.monolith.simple_mall.controller;

import io.homo_efficio.monolith.simple_mall.domain.repository.CustomerRepository;
import io.homo_efficio.monolith.simple_mall.dto.CustomerIn;
import io.homo_efficio.monolith.simple_mall.dto.CustomerOut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
