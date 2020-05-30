package io.homo_efficio.monolith.simple_mall._common.exception.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-30
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldErr {

    private String field;
    private String value;
    private String reason;

    static List<FieldErr> from(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(e -> new FieldErr(
                        e.getField(),
                        e.getRejectedValue() != null ? e.getRejectedValue().toString() : "",
                        e.getDefaultMessage()
                ))
                .collect(Collectors.toList());
    }
}
