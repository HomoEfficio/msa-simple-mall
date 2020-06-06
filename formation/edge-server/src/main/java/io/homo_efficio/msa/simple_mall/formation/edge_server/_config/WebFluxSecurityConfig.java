package io.homo_efficio.msa.simple_mall.formation.edge_server._config;

import io.homo_efficio.msa.simple_mall.formation.edge_server.auth.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-06-06
 */
@EnableWebFluxSecurity
@RequiredArgsConstructor
@Slf4j
public class WebFluxSecurityConfig {

    private final SellerRepository sellerRepository;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeExchange()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers(HttpMethod.POST, "/v1/sellers").permitAll()
                .anyExchange().authenticated()
                .and()
                .build()
                ;
    }

    @Bean
    public ReactiveUserDetailsService sellerUserDetailsServie() {
        return username -> sellerRepository.findByLoginId(username)
                .map(seller -> User.withUsername(seller.getLoginId())
                        .password(seller.getPassword())
                        .roles("SELLER")
                        .build());
    }
}
