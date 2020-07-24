package io.homo_efficio.biz.common.exception.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-07-24
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldError {

    private String field;
    private String value;
    private String reason;

    static List<FieldError> from(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(e -> new FieldError(
                        e.getField(),
                        e.getRejectedValue() != null ? e.getRejectedValue().toString() : "",
                        e.getDefaultMessage()
                ))
                .collect(Collectors.toList());
    }
}
