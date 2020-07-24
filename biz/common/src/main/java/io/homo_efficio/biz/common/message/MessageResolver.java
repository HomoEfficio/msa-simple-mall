package io.homo_efficio.biz.common.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-07-24
 */
@Component
@RequiredArgsConstructor
public class MessageResolver {

    private final MessageSource messageSource;


    public String getMessage(String code, Object... args) {
        String message;
        try {
            message = messageSource.getMessage(code, args, Locale.getDefault());
        } catch (NoSuchMessageException ex) {
            message = ex.getMessage();
        }
        return message;
    }
}
