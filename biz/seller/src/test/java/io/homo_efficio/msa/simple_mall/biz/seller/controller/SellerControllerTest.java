package io.homo_efficio.msa.simple_mall.biz.seller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.homo_efficio.msa.simple_mall.biz.seller.domain.model.Seller;
import io.homo_efficio.msa.simple_mall.biz.seller.dto.SellerIn;
import io.homo_efficio.msa.simple_mall.biz.seller.dto.SellerOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureWebTestClient
class SellerControllerTest {

    @Autowired
    private WebTestClient client;

    private JacksonTester<SellerIn> sellerInTester;

    @BeforeEach
    void beforeEach() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @ParameterizedTest(name = "판매자 [{0} - {1} - {2} - {3} - {4}] 생성")
    @MethodSource("sellers")
    void create(String name, String email, String phone, String loginId, String password) {
        postNewSeller(name, email, phone, loginId, password)
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("loginId").isEqualTo(loginId)
                .jsonPath("name").isEqualTo(name)
                .jsonPath("email").isEqualTo(email)
                .jsonPath("phone").isEqualTo(phone)
        ;
    }

    private WebTestClient.ResponseSpec postNewSeller(String name, String email, String phone, String loginId, String password) {
        return client
                .post()
                .uri("/sellers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new SellerIn(name, email, phone, loginId, password)), Seller.class)
                .exchange()
                ;
    }

    private static Stream<Arguments> sellers() {
        return Stream.of(
                Arguments.of("마이크로서비스학생1", "user1@test.com", "010-1111-1111", "user1", "p123456"),
                Arguments.of("a", "ab@t.c", "123456789", "login", "passwd"),
                Arguments.of("열글자로된판매자이름", "aaaaaaaaaabbbbbbbbbbcccccccccc@dddddddd.eeeeeeeeee", "12345678901234567890", "aaaaaaaaaabbbbbbbbbbcccccccccc", "abcde12345abcde12345")
        );
    }

    @DisplayName("판매자 정보 수정: name 이 [마이크로서비스학생1]인 판매자 이름을 [대박기원판매자1]로 변경한다.")
    @Test
    void 판매자_수정_01() {
        FluxExchangeResult<SellerOut> sellerOutFluxExchangeResult = client
                .post()
                .uri("/sellers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new SellerIn("마이크로서비스학생1", "user1@test.com", "010-1111-1111", "user1", "p123456")), Seller.class)
                .exchange()
                .expectStatus().isOk()
                .returnResult(SellerOut.class);

        Flux<SellerOut> responseBody = sellerOutFluxExchangeResult.getResponseBody();
        SellerOut sellerOut = responseBody.blockFirst();
        String sellerId = Objects.requireNonNull(sellerOut).getId();

        client
                .put()
                .uri("/sellers/" + sellerId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new SellerIn("대박기원판매자1", "user1@test.com", "010-1111-1111", "user1", "p123456")), Seller.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(sellerId)
                .jsonPath("loginId").isEqualTo("user1")
                .jsonPath("name").isEqualTo("대박기원판매자1")
                .jsonPath("email").isEqualTo("user1@test.com")
                .jsonPath("phone").isEqualTo("010-1111-1111")
        ;
    }

    @DisplayName("판매자 정보 수정: 존재하지 않는 sellerId로 판매자 정보 수정 시 404 를 반환한다.")
    @Test
    void 판매자_수정_02() {
        String dummySellerId = "-----";

            client
                    .put()
                    .uri("/sellers/" + dummySellerId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(new SellerIn("대박기원판매자1", "user1@test.com", "010-1111-1111", "user1", "p123456")), Seller.class)
                    .exchange()
                    .expectStatus().isNotFound()
            ;
    }
}
