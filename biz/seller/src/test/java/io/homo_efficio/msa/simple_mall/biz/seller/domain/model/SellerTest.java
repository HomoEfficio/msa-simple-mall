package io.homo_efficio.msa.simple_mall.biz.seller.domain.model;

import io.homo_efficio.msa.simple_mall.biz.seller.domain.repository.SellerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SellerTest {

    @Autowired
    private SellerRepository sellerRepository;

    @ParameterizedTest(name = "이름 [{0}], 이메일 [{1}], 로그인아이디 [{2}], 비밀번호 [{3}], 전화번호 [{4}]인 판매자 계정이 생성된다.")
    @MethodSource("sellers")
    void 새_판매자_계정_생성(String name, String email, String loginId, String password, String phone) {
        Mono<Seller> sellerMono = sellerRepository.save(new Seller(
                null, name, email, loginId, password, phone
        ));

        StepVerifier
                .create(sellerMono)
                .assertNext(seller -> {
                    assertThat(seller.getId()).isNotNull();
                    System.out.println(seller.getId());
                    assertThat(seller.getName()).isEqualTo(name);
                    assertThat(seller.getEmail()).isEqualTo(email);
                    assertThat(seller.getLoginId()).isEqualTo(loginId);
                    assertThat(seller.getPassword()).isEqualTo(password);
                    assertThat(seller.getPhone()).isEqualTo(phone);
                })
                .expectComplete()
                .verify();
    }

    private static Stream<Arguments> sellers() {
        return Stream.of(
                Arguments.of("마이크로서비스학생1", "user1@test.com", "010-1111-1111", "user1", "p123456"),
                Arguments.of("a", "ab@t.c", "123456789", "login", "passwd"),
                Arguments.of("열글자로된판매자이름", "aaaaaaaaaabbbbbbbbbbcccccccccc@dddddddd.eeeeeeeeee", "12345678901234567890", "aaaaaaaaaabbbbbbbbbbcccccccccc", "abcde12345abcde12345")
        );
    }

}
