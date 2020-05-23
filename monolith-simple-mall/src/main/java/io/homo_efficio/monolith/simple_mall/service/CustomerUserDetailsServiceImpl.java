package io.homo_efficio.monolith.simple_mall.service;

import io.homo_efficio.monolith.simple_mall._common.exception.EntityNotFoundException;
import io.homo_efficio.monolith.simple_mall.domain.Customer;
import io.homo_efficio.monolith.simple_mall.domain.Seller;
import io.homo_efficio.monolith.simple_mall.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-17
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerUserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByLoginId(username)
                .orElseThrow(() -> new EntityNotFoundException(Seller.class,
                        String.format("LoginId [%s] 인 Customer 는 존재하지 않습니다.", username)));
        User.UserBuilder userBuilder =
                User.withUsername(username)
                    .password(customer.getPassword())
                    .roles("USER");
        return userBuilder.build();
    }
}
