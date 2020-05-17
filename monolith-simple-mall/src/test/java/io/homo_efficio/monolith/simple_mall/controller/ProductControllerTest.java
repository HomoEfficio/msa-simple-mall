package io.homo_efficio.monolith.simple_mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.homo_efficio.monolith.simple_mall.dto.ProductIn;
import io.homo_efficio.monolith.simple_mall.dto.ProductOut;
import io.homo_efficio.monolith.simple_mall.dto.SellerIn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@SpringBootTest
@Transactional
public class ProductControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    private JacksonTester<ProductIn> productInTester;
    private JacksonTester<ProductOut> productOutTester;
    private JacksonTester<SellerIn> sellerInTester;


    @BeforeEach
    public void beforeEach() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @ParameterizedTest(name = "상품 [{0} - {1} - {2} - {3} - {4}] 생성")
    @MethodSource("products")
    public void create(String name, String sellerLoginId, String password, BigDecimal price, Long count) throws Exception {
        postNewSeller(sellers());

        postNewProduct(name, sellerLoginId, password, price, count)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").value(name))
                .andExpect(jsonPath("seller.loginId").value(sellerLoginId))
                .andExpect(jsonPath("price").value(price))
                .andExpect(jsonPath("count").value(count))
        ;
    }

    private ResultActions postNewProduct(String name, String sellerLoginId, String password, BigDecimal price, Long count) throws Exception {
        return mvc.perform(
                post("/v1/products")
                        .with(httpBasic(sellerLoginId, password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(
                                productInTester.write(
                                        new ProductIn(name, null, sellerLoginId, null, price, count)
                                ).getJson())
        );
    }

    @ParameterizedTest(name = "상품 [{0} - {1} - {2} - {3} - {4} | {5}] 이름, 설명 수정")
    @MethodSource("updatedProducts")
    public void update(String name, String sellerLoginId, String password, BigDecimal price, Long count, ProductIn productIn) throws Exception {
        postNewSeller(sellers());
        ResultActions resultActions = postNewProduct(name, sellerLoginId, password, price, count);
        MvcResult mvcResult = resultActions.andReturn();
        ProductOut productOut = productOutTester.parseObject(mvcResult.getResponse().getContentAsString());
        Long id = productOut.getId();

        mvc.perform(
                put("/v1/products/" + id)
                        .with(httpBasic(sellerLoginId, password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(productInTester.write(productIn).getJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("name").value(productIn.getName()))
                .andExpect(jsonPath("description").value(productIn.getDescription()))
        ;
    }

    private static Stream<Arguments> updatedProducts() {
        return products()
                .map(Arguments::get)
                .map(pArr -> {
                    String name = pArr[0].toString();
                    String sellerLoginId = pArr[1].toString();
                    String password = pArr[2].toString();
                    BigDecimal price = (BigDecimal) pArr[3];
                    long count = (long) pArr[4];
                    return Arguments.of(name, sellerLoginId, password, price, count,
                            new ProductIn(
                                    name,
                                    name + " 설명",
                                    sellerLoginId,
                                    "manufacturer" + (int) (Math.random() * 100),
                                    price,
                                    count));
                });
    }

    private static Stream<Arguments> products() {
        return Stream.of(
                Arguments.of("상품 101", "Seller01", "p123456", BigDecimal.valueOf(10_000L), 111L),
                Arguments.of("상품 102", "Seller01", "p123456", BigDecimal.valueOf(20_000L), 222L),
                Arguments.of("상품 103", "Seller01", "p123456", BigDecimal.valueOf(30_000L), 333L),

                Arguments.of("상품 201", "Seller02", "passwd", BigDecimal.valueOf(10_000L), 111L),
                Arguments.of("상품 202", "Seller02", "passwd", BigDecimal.valueOf(20_000L), 222L),
                Arguments.of("상품 203", "Seller02", "passwd", BigDecimal.valueOf(30_000L), 333L),

                Arguments.of("상품 301", "Seller03", "abcde12345abcde12345", BigDecimal.valueOf(10_000L), 111L),
                Arguments.of("상품 302", "Seller03", "abcde12345abcde12345", BigDecimal.valueOf(20_000L), 222L),
                Arguments.of("상품 303", "Seller03", "abcde12345abcde12345", BigDecimal.valueOf(30_000L), 333L)
        );
    }

    private void postNewSeller(List<String[]> sellers) throws Exception {
        for (String[] sellerInfo : sellers) {
            mvc.perform(
                    post("/v1/sellers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(sellerInTester.write(new SellerIn(sellerInfo[0], sellerInfo[1], sellerInfo[2], sellerInfo[3], sellerInfo[4])).getJson()));
        }
    }

    private static List<String[]> sellers() {
        return List.of(
                new String[]{"Seller01", "user1@test.com", "010-1111-1111", "Seller01", "p123456"},
                new String[]{"Seller02", "ab@t.c", "123456789", "Seller02", "passwd"},
                new String[]{"Seller03", "aaaaaaaaaabbbbbbbbbbcccccccccc@dddddddd.eeeeeeeeee", "12345678901234567890", "Seller03", "abcde12345abcde12345"}
        );
    }
}
