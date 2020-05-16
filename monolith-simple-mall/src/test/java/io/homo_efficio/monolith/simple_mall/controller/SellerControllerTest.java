package io.homo_efficio.monolith.simple_mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.homo_efficio.monolith.simple_mall.dto.SellerIn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MockMvc 테스트에서는 별도로 설정해주지 않으면 Filter 는 적용되지 않는다.
 * https://github.com/HomoEfficio/dev-tips/blob/master/Spring-Boot-MockMvc로-Filter-테스트.md
 *
 * Security Filter 도 적용되지 않으므로
 * MockMvc 로 테스트하면 모든 API 호출이 열려있게 된다.
 */
@SpringBootTest
@Transactional
class SellerControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    private JacksonTester<SellerIn> sellerInTester;


    @BeforeEach
    public void beforeEach() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @ParameterizedTest(name = "판매자 [{0} - {1} - {2} - {3} - {4}] 생성")
    @MethodSource("sellers")
    void create(String name, String email, String phone, String loginId, String password) throws Exception {
        postNewSeller(name, email, phone, loginId, password)
                .andExpect(status().isOk())
                .andExpect(jsonPath("loginId").value(loginId))
                .andExpect(jsonPath("name").value(name))
                .andExpect(jsonPath("email").value(email))
                .andExpect(jsonPath("phone").value(phone))
        ;
    }

    private ResultActions postNewSeller(String name, String email, String phone, String loginId, String password) throws Exception {
        return mvc.perform(
                post("/v1/sellers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(sellerInTester.write(new SellerIn(name, email, phone, loginId, password)).getJson()));
    }

    private static Stream<Arguments> sellers() {
        return Stream.of(
                Arguments.of("마이크로서비스학생1", "user1@test.com", "010-1111-1111", "user1", "p123456"),
                Arguments.of("a", "ab@t.c", "123456789", "login", "passwd"),
                Arguments.of("열글자로된판매자이름", "aaaaaaaaaabbbbbbbbbbcccccccccc@dddddddd.eeeeeeeeee", "12345678901234567890", "aaaaaaaaaabbbbbbbbbbcccccccccc", "abcde12345abcde12345")
        );
    }

    @ParameterizedTest(name = "판매자 [{0} - {1} - {2} - {3} - {4}, {5}] 수정")
    @MethodSource("updatedSellers")
    void update(String name, String email, String phone, String loginId, String password, SellerIn updatedSellerIn) throws Exception {
        postNewSeller(name, email, phone, loginId, password);

        mvc.perform(
                put("/v1/sellers?loginId=" + loginId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(sellerInTester.write(updatedSellerIn).getJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("loginId").value(updatedSellerIn.getLoginId()))
                .andExpect(jsonPath("name").value(updatedSellerIn.getName()))
                .andExpect(jsonPath("email").value(updatedSellerIn.getEmail()))
                .andExpect(jsonPath("phone").value(updatedSellerIn.getPhone()))
        ;
    }

    private static Stream<Arguments> updatedSellers() {
        return Stream.of(
                Arguments.of("마이크로서비스학생1", "user1@test.com", "010-1111-1111", "user1", "p123456",
                        new SellerIn("마이크로서비스학생2", "user2@test.com", "010-2222-2222", "user1", "p223456")),
                Arguments.of("a", "ab@t.c", "123456789", "login", "passwd",
                        new SellerIn("b", "bc@d.e", "987654321", "login", "PASSWD")),
                Arguments.of("열글자로된판매자이름", "aaaaaaaaaabbbbbbbbbbcccccccccc@dddddddd.eeeeeeeeee", "12345678901234567890", "aaaaaaaaaabbbbbbbbbbcccccccccc", "abcde12345abcde12345",
                        new SellerIn("열글자로된판매자이름", "aaaaaaaaaabbbbbbbbbbcccccccccc@dddddddd.eeeeeeeeee", "12345678901234567890", "aaaaaaaaaabbbbbbbbbbcccccccccc", "Abcde12345abcde12345"))
        );
    }


    @Test
    void delete() {
    }

    @Test
    void findByLoginId() {
    }
}
