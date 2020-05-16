package io.homo_efficio.monolith.simple_mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MonolithSimpleMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonolithSimpleMallApplication.class, args);
    }

}
