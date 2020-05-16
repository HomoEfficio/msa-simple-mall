package io.homo_efficio.monolith.simple_mall._config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/sellers").permitAll()
                .anyRequest().authenticated()
        ;
    }
}

