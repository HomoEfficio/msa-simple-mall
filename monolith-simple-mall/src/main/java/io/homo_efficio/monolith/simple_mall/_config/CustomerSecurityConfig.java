package io.homo_efficio.monolith.simple_mall._config;

import io.homo_efficio.monolith.simple_mall.domain.repository.CustomerRepository;
import io.homo_efficio.monolith.simple_mall.service.CustomerUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@EnableWebSecurity
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomerSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic()
                .and()
                .requestMatchers()
                .antMatchers("/v1/product-reviews")
                .antMatchers("/v1/customers")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/v1/customers").permitAll()
                .anyRequest().authenticated()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new CustomerUserDetailsServiceImpl(customerRepository)).passwordEncoder(passwordEncoder);
    }
}

