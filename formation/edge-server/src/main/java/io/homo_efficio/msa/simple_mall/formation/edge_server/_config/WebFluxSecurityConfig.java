package io.homo_efficio.msa.simple_mall.formation.edge_server._config;

import io.homo_efficio.msa.simple_mall.formation.edge_server.auth.Customer;
import io.homo_efficio.msa.simple_mall.formation.edge_server.auth.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-06-06
 */
@EnableWebFluxSecurity
@RequiredArgsConstructor
@Slf4j
public class WebFluxSecurityConfig {

    private final SellerRepository sellerRepository;
    private final DatabaseClient dbClient;

    @Bean
    @Order(1)
    public SecurityWebFilterChain customerSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .httpBasic()
                .and()
                // /v1/customers 로 들어오는 요청만 이 필터에서 처리
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/v1/customers"))
                .authenticationManager(
                        new UserDetailsRepositoryReactiveAuthenticationManager(
                                username -> dbClient
                                        .select()
                                        .from(Customer.class)
                                        .matching(Criteria.where("login_id").is(username))
                                        .fetch()
                                        .one()
                                        .map(customer -> User.withUsername(customer.getLoginId())
                                                .password(customer.getPassword())
                                                .roles("CUSTOMER")
                                                .build())
                        )
                )
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/v1/customers")
                .permitAll()
                .anyExchange()
                .authenticated()
                .and()
                .build();
    }

    @Bean
    @Order(2)
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .httpBasic()
                .and()
                .authenticationManager(
                        new UserDetailsRepositoryReactiveAuthenticationManager(
                                username -> sellerRepository.findByLoginId(username)
                                        .map(seller -> User.withUsername(seller.getLoginId())
                                                .password(seller.getPassword())
                                                .roles("SELLER")
                                                .build())
                        )
                )
                .authorizeExchange()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers(HttpMethod.POST, "/v1/sellers").permitAll()
                .anyExchange().authenticated()
                .and()
                .build()
                ;
    }

}
