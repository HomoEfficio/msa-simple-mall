package io.homo_efficio.msa.simple_mall.biz.seller._config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-07-24
 */
@Configuration
@ComponentScan("io.homo_efficio.biz.common")
public class CommonImportConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:message/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
