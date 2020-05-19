package io.homo_efficio.monolith.simple_mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.homo_efficio.monolith.simple_mall.domain.Address;
import io.homo_efficio.monolith.simple_mall.dto.CustomerIn;
import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class CustomerControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    private JacksonTester<CustomerIn> customerInTester;


    @BeforeEach
    public void beforeEach() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @ParameterizedTest(name = "고객 [{0} - {1} - {2} - {3} - {4} - {5}] 생성")
    @MethodSource("customers")
    public void create(String name, String email, String phone, Address address, String loginId, String password) throws Exception {
        postNewCustomer(name, email, phone, address, loginId, password)
                .andExpect(status().isOk())
                .andExpect(jsonPath("loginId").value(loginId))
                .andExpect(jsonPath("name").value(name))
                .andExpect(jsonPath("email").value(email))
                .andExpect(jsonPath("phone").value(phone))
                .andExpect(jsonPath("address.zipNo").value(address.getZipNo()))
                .andExpect(jsonPath("address.roadAddrPart1").value(address.getRoadAddrPart1()))
                .andExpect(jsonPath("address.roadAddrPart2").value(address.getRoadAddrPart2()))
                .andExpect(jsonPath("address.addrDetail").value(address.getAddrDetail()))
        ;
    }

    private ResultActions postNewCustomer(String name, String email, String phone, Address address, String loginId, String password) throws Exception {
        return mvc.perform(
                post("/v1/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(customerInTester.write(new CustomerIn(name, email, phone, address, loginId, password)).getJson())
        );
    }

    private static Stream<Arguments> customers() {
        return Stream.of(
                Arguments.of("열글자로된고객이름일", "aaaaaaaaaabbbbbbbbbbcccccccccc@dddddddd.eeeeeeeeee", "12345678901234567890", new Address("12345", "도로명 주소 11", "도로명 주소 12", "상세주소 1"), "aaaaaaaaaabbbbbbbbbbcccccccccc", "abcde12345abcde12345"),
                Arguments.of("열글자로된고객이름이", "smile-customer@gmail.com", "12345678901234567890", new Address("54321", "도로명 주소 21", "도로명 주소 22", "상세주소 2"), "smile-customer", "12345abcde12345abcde")
        );
    }

}
