package io.homo_efficio.monolith.simple_mall.domain.repository;

import io.homo_efficio.monolith.simple_mall.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.Optional;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByLoginId(String loginId);
    Optional<Seller> findByEmail(@Email String email);
    Optional<Seller> findById(Long id);

    List<Seller> findAllByNameContaining(String name);
    List<Seller> findAllByPhoneContaining(String phone);
}
