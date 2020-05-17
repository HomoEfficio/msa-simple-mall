package io.homo_efficio.monolith.simple_mall.controller;

import io.homo_efficio.monolith.simple_mall._common.exception.EntityNotFoundException;
import io.homo_efficio.monolith.simple_mall.domain.Seller;
import io.homo_efficio.monolith.simple_mall.domain.repository.SellerRepository;
import io.homo_efficio.monolith.simple_mall.dto.SellerIn;
import io.homo_efficio.monolith.simple_mall.dto.SellerOut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@RestController
@RequestMapping("/v1/sellers")
@RequiredArgsConstructor
@Transactional
public class SellerController {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<SellerOut> create(@RequestBody SellerIn sellerIn) {
        return ResponseEntity.ok(SellerOut.from(sellerRepository.save(sellerIn.toEntityWithPasswordEncoder(passwordEncoder))));
    }

    @PutMapping
    public ResponseEntity<SellerOut> update(@RequestParam("loginId") String loginId, @RequestBody SellerIn sellerIn) {
        Seller seller = sellerRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException(Seller.class,
                        String.format("판매자 [%s] 는 존재하지 않습니다.", loginId)));
        sellerIn.updateEntityWithPasswordEncoder(seller, passwordEncoder);
        return ResponseEntity.ok(SellerOut.from(seller));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam("loginId") String loginId) {
        Seller seller = sellerRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException(Seller.class,
                        String.format("판매자 [%s] 는 존재하지 않습니다.", loginId)));
        sellerRepository.delete(seller);
        return ResponseEntity.ok(loginId);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<SellerOut> findByLoginId(@RequestParam("loginId") String loginId) {
        return ResponseEntity.ok(
                SellerOut.from(
                        sellerRepository.findByLoginId(loginId)
                                .orElseThrow(() -> new EntityNotFoundException(Seller.class,
                                        String.format("판매자 [%s] 는 존재하지 않습니다.", loginId)))));
    }

}
