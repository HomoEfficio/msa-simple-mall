package io.homo_efficio.msa.simple_mall.formation.edge_server._config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

/**
 * https://docs.spring.io/spring-data/r2dbc/docs/1.1.0.RELEASE/reference/html/#r2dbc.connecting
 *
 * @author homo.efficio@gmail.com
 * created on 2020-06-06
 */
@Configuration
//@EnableR2dbcRepositories  // 없어도 찾음
@RequiredArgsConstructor
public class R2DBCConfig extends AbstractR2dbcConfiguration {

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                        .option(DRIVER, "postgresql")
                        .option(HOST, "localhost")
                        .option(PORT, 5432)
                        .option(DATABASE, "monolith-simple-mall")
                        .option(USER, "user")
                        .option(PASSWORD, "pwd")
                        .build()
        );
    }
}
