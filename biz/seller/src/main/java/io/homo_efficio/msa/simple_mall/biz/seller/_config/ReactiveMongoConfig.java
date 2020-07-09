package io.homo_efficio.msa.simple_mall.biz.seller._config;

import com.mongodb.reactivestreams.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-07-09
 */
@Configuration
@RequiredArgsConstructor
public class ReactiveMongoConfig {

    private final MongoClient mongoClient;

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient, "test");
    }
}
