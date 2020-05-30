package io.homo_efficio.monolith.simple_mall._config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-30
 */
@Configuration
public class MessageConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:message/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public MessageResolver messageResolver() {
        return new MessageResolver(messageSource());
    }


    @RequiredArgsConstructor
    public static class MessageResolver {

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
}
