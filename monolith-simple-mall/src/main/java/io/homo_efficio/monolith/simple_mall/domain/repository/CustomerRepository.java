package io.homo_efficio.monolith.simple_mall.domain.repository;

import io.homo_efficio.monolith.simple_mall.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.Optional;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-19
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByLoginId(String loginId);
    Optional<Customer> findByEmail(@Email String email);
    Optional<Customer> findById(Long id);

    List<Customer> findAllByNameContaining(String name);
    List<Customer> findAllByPhoneContaining(String phone);
}
