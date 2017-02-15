package com.nixsolutions.chursin.springlab.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by chursin on 01.08.16.
 */
@Configuration
public class CaptchaConfig {

    @Bean
    public Producer kaptchaProducer() {
        Config config = new Config(new Properties(null));
        DefaultKaptcha kaptchaProducer = new DefaultKaptcha();
        kaptchaProducer.setConfig(config);
        return kaptchaProducer;
    }
}
