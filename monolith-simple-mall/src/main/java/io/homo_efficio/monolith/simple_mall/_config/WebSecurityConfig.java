package io.homo_efficio.monolith.simple_mall._config;

import io.homo_efficio.monolith.simple_mall.domain.repository.SellerRepository;
import io.homo_efficio.monolith.simple_mall.service.SellerUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.List;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SellerRepository sellerRepository;
    @Value("${custom.auth.in-memory-user.username:tester}")
    private String username;
    @Value("${custom.auth.in-memory-user.password:{noop}Tester}")
    private String password;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/sellers").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .headers()
                .addHeaderWriter(
                        new XFrameOptionsHeaderWriter(
                                new WhiteListedAllowFromStrategy(List.of("localhost"))    // 여기!
                        )
                )
                .frameOptions().sameOrigin()
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(username).password(password).roles("ADMIN");
        auth.userDetailsService(new SellerUserDetailsServiceImpl(sellerRepository)).passwordEncoder(passwordEncoder());
    }
}

