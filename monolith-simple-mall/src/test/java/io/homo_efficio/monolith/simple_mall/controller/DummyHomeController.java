package io.homo_efficio.monolith.simple_mall.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Basic Auth WebMvcTest 에만 사용
 *
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@RestController
public class DummyHomeController {

    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("You are authenticated!!");
    }
}
