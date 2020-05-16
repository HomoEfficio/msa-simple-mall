package io.homo_efficio.monolith.simple_mall.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@WebMvcTest(DummyHomeController.class)
public class BasicAuthTest {

    @Autowired
    private MockMvc mvc;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @DisplayName("Basic Auth 인증 정보를 헤더에 담아 / 로 요청을 보내면 인증을 통과하고 200 OK 반환")
    @Test
    public void basicAuthSuccessTest() throws Exception {
        mvc.perform(
                get("/")
                        .with(httpBasic(username, password)))  // WebSecurityConfig 에 지정된 인증 정보
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("Basic Auth 인증 정보 없이 / 로 요청을 보내면 인증을 통과하지 못하고 401 Unauthorized 반환")
    @Test
    public void basicAuthFailTest() throws Exception {
        mvc.perform(
                get("/"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }
}
