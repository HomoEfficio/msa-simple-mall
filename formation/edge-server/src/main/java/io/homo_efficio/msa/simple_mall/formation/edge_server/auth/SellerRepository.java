package io.homo_efficio.msa.simple_mall.formation.edge_server.auth;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-06-06
 */
public interface SellerRepository extends R2dbcRepository<Seller, Long> {

    Mono<Seller> findByLoginId(String loginId);
}
