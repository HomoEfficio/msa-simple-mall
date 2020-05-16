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

    @ParameterizedTest(name = "상품 [{0} - {1} - {2} - {3}] 생성")
    @MethodSource("products")
    public void create(String name, String sellerLoginId, BigDecimal price, Long count) throws Exception {
        postNewSeller(sellers());

        postNewProduct(name, sellerLoginId, price, count)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").value(name))
                .andExpect(jsonPath("seller.loginId").value(sellerLoginId))
                .andExpect(jsonPath("price").value(price))
                .andExpect(jsonPath("count").value(count))
        ;
    }

    private ResultActions postNewProduct(String name, String sellerLoginId, BigDecimal price, Long count) throws Exception {
        return mvc.perform(
                post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(
                                productInTester.write(
                                        new ProductIn(name, null, sellerLoginId, null, price, count)
                                ).getJson())
        );
    }

    @ParameterizedTest(name = "상품 [{0} - {1} - {2} - {3} | {4} - {5}] 이름, 설명 수정")
    @MethodSource("updatedProducts")
    public void update(String name, String sellerLoginId, BigDecimal price, Long count, ProductIn productIn) throws Exception {
        postNewSeller(sellers());
        ResultActions resultActions = postNewProduct(name, sellerLoginId, price, count);
        MvcResult mvcResult = resultActions.andReturn();
        ProductOut productOut = productOutTester.parseObject(mvcResult.getResponse().getContentAsString());
        Long id = productOut.getId();

        mvc.perform(
                put("/v1/products/" + id)
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
        return Stream.of(
                Arguments.of("상품 101", "Seller01", BigDecimal.valueOf(10_000L), 111L,
                        new ProductIn(
                                "상품 101",
                                "상품 101 설명",
                                "Seller01",
                                "manufacturer" + (int) (Math.random() * 100),
                                BigDecimal.valueOf(10_000L),
                                111L)),
                Arguments.of("상품 102", "Seller01", BigDecimal.valueOf(20_000L), 222L,
                        new ProductIn(
                                "상품 102",
                                "상품 102 설명",
                                "Seller01",
                                "manufacturer" + (int) (Math.random() * 100),
                                BigDecimal.valueOf(20_000L),
                                222L)),
                Arguments.of("상품 103", "Seller01", BigDecimal.valueOf(30_000L), 333L,
                        new ProductIn(
                                "상품 103",
                                "상품 103 설명",
                                "Seller01",
                                "manufacturer" + (int) (Math.random() * 100),
                                BigDecimal.valueOf(30_000L),
                                333L)),

                Arguments.of("상품 201", "Seller02", BigDecimal.valueOf(10_000L), 111L,
                        new ProductIn(
                                "상품 201",
                                "상품 201 설명",
                                "Seller02",
                                "manufacturer" + (int) (Math.random() * 100),
                                BigDecimal.valueOf(10_000L),
                                111L)),
                Arguments.of("상품 202", "Seller02", BigDecimal.valueOf(20_000L), 222L,
                        new ProductIn(
                                "상품 202",
                                "상품 202 설명",
                                "Seller02",
                                "manufacturer" + (int) (Math.random() * 200),
                                BigDecimal.valueOf(20_000L),
                                222L)),
                Arguments.of("상품 203", "Seller02", BigDecimal.valueOf(30_000L), 333L,
                        new ProductIn(
                                "상품 203",
                                "상품 203 설명",
                                "Seller03",
                                "manufacturer" + (int) (Math.random() * 300),
                                BigDecimal.valueOf(30_000L),
                                333L)),

                Arguments.of("상품 301", "Seller03", BigDecimal.valueOf(10_000L), 111L,
                        new ProductIn(
                                "상품 301",
                                "상품 301 설명",
                                "Seller03",
                                "manufacturer" + (int) (Math.random() * 100),
                                BigDecimal.valueOf(10_000L),
                                111L)),
                Arguments.of("상품 302", "Seller03", BigDecimal.valueOf(20_000L), 222L,
                        new ProductIn(
                                "상품 302",
                                "상품 302 설명",
                                "Seller03",
                                "manufacturer" + (int) (Math.random() * 100),
                                BigDecimal.valueOf(20_000L),
                                222L)),
                Arguments.of("상품 303", "Seller03", BigDecimal.valueOf(30_000L), 333L,
                        new ProductIn(
                                "상품 303",
                                "상품 303 설명",
                                "Seller03",
                                "manufacturer" + (int) (Math.random() * 100),
                                BigDecimal.valueOf(30_000L),
                                333L))
        );

//        return products()
//                .map(Arguments::get)
//                .map(pArr -> {
//                    return Arguments.of(pArr,
//                            new ProductIn(
//                                    pArr[0].toString(),
//                                    pArr[0].toString() + " 설명",
//                                    pArr[1].toString(),
//                                    "manufacturer" + (int) (Math.random() * 100),
//                                    (BigDecimal)pArr[2],
//                                    (long)pArr[3]));
//                });

//        List<Arguments> updatedProducts = new ArrayList<>();
//        List<Arguments> args = products().collect(Collectors.toList());
//        for (Arguments arg : args) {
//            Object[] p = arg.get();
//            updatedProducts.add(Arguments.of(p, new ProductIn(
//                    p[0].toString(),
//                    p[0].toString() + " 설명",
//                    p[1].toString(),
//                    "manufacturer" + (int) (Math.random() * 100),
//                    (BigDecimal)p[2],
//                    (long)p[3])));
//        }
//        return updatedProducts.stream();
    }

    private static Stream<Arguments> products() {
        return Stream.of(
                Arguments.of("상품 101", "Seller01", BigDecimal.valueOf(10_000L), 111L),
                Arguments.of("상품 102", "Seller01", BigDecimal.valueOf(20_000L), 222L),
                Arguments.of("상품 103", "Seller01", BigDecimal.valueOf(30_000L), 333L),

                Arguments.of("상품 201", "Seller02", BigDecimal.valueOf(10_000L), 111L),
                Arguments.of("상품 202", "Seller02", BigDecimal.valueOf(20_000L), 222L),
                Arguments.of("상품 203", "Seller02", BigDecimal.valueOf(30_000L), 333L),

                Arguments.of("상품 301", "Seller03", BigDecimal.valueOf(10_000L), 111L),
                Arguments.of("상품 302", "Seller03", BigDecimal.valueOf(20_000L), 222L),
                Arguments.of("상품 303", "Seller03", BigDecimal.valueOf(30_000L), 333L)
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
