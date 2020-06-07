package io.homo_efficio.msa.simple_mall.formation.edge_server;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.Data;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class EdgeServerApplicationTests {

    @Value("${upstream.monolith.port}")
    void setUpstreamServerPort(int port) {
        if (wireMockServer == null) {
            wireMockServer =
                    new WireMockServer(WireMockSpring.options().port(port));
            wireMockServer.start();
        }
    }

    private static WireMockServer wireMockServer;

    private WebTestClient client;

    @Autowired
    private Environment environment;


    @BeforeEach
    void beforeEach() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + environment.getProperty("server.port")).build();
    }

    @AfterEach
    void afterEach() {
        wireMockServer.resetAll();
    }

    @AfterAll
    static void afterAll() {
        if (wireMockServer != null) {
            wireMockServer.shutdown();
        }
    }


    @DisplayName("인증 없이 Seller를 등록하면 200이 반환된다.")
    @Test
    void createSeller() {
        wireMockServer.stubFor(
                WireMock.post(WireMock.urlEqualTo("/v1/sellers"))
                        .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK).withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        );

        Seller seller = new Seller();
        seller.setName("HomoEfficio");
        seller.setPassword("password");
        seller.setEmail("homo.efficio@gmail.com");
        seller.setPhone("010-8888-9999");
        seller.setLoginId("homoefficio");


        client.post().uri("/v1/sellers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(seller), Seller.class)
                .exchange()
                .expectStatus().isOk();
    }

    @DisplayName("인증 없이 Seller를 조회하면 401이 반환된다.")
    @Test
    void findSellerWithoutAuthenticated() {

        wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo("/v1/sellers/1"))
                        .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK))
        );


        client.get()
                .uri("/v1/sellers/1")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Data
    static class Seller {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private String loginId;
        private String password;
    }

}
