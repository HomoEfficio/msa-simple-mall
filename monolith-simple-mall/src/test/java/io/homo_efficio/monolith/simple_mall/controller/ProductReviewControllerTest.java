package io.homo_efficio.monolith.simple_mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.homo_efficio.monolith.simple_mall.dto.ProductReviewIn;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductReviewControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    private JacksonTester<ProductReviewIn> productReviewInTester;

    @Autowired
    private DataSource dataSource;

    @BeforeAll
    public void beforeAll() throws Exception {
        System.out.println("BeforeAll");
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/data/insert-customer-seller-product.sql"));
        }
    }

    @AfterAll
    public void afterAll() throws Exception {
        System.out.println("AfterAll");
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/data/truncate-customer-seller-product.sql"));
        }
    }

    @BeforeEach
    public void beforeEach() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @ParameterizedTest(name = "상품 {0} 에 대한 고객 {2}/{3} 의 리뷰 {4} 생성")
    @MethodSource("productReviews")
    public void create(Long productId, Long customerId, String customerLoginId, String password, String comment) throws Exception {
        postNewProductReview(productId, customerId, customerLoginId, password, comment)
                .andExpect(status().isOk())
                .andExpect(jsonPath("productId").value(productId))
                .andExpect(jsonPath("productName").exists())
                .andExpect(jsonPath("customerId").value(customerId))
                .andExpect(jsonPath("customerName").exists())
                .andExpect(jsonPath("comment").value(comment))
        ;
    }

    private static Stream<Arguments> productReviews() {
        return Stream.of(
                Arguments.of(1L, 1L, "aaaaaaaaaabbbbbbbbbbcccccccccc", "abcde12345abcde12345", "1 신박한 상품이네여~"),
                Arguments.of(2L, 1L, "aaaaaaaaaabbbbbbbbbbcccccccccc", "abcde12345abcde12345", "1 이거 사려고 20년을 기다렸습니다."),
                Arguments.of(3L, 1L, "aaaaaaaaaabbbbbbbbbbcccccccccc", "abcde12345abcde12345", "1 이 가격 말이 되나요?"),
                Arguments.of(1L, 2L, "smile-customer", "12345abcde12345abcde", "2 보기만 해도 가슴이 벅차오릅니다."),
                Arguments.of(2L, 2L, "smile-customer", "12345abcde12345abcde", "2 사진과 너무 다릅니다.")
        );
    }

    private ResultActions postNewProductReview(Long productId, Long customerId, String customerLoginId, String password, String comment) throws Exception {
        return mvc.perform(post("/v1/product-reviews")
                .with(httpBasic(customerLoginId, password))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(productReviewInTester.write(new ProductReviewIn(productId, customerId, comment)).getJson()));
    }
}
