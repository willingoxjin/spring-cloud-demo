package org.willingoxjin.sentinel.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin.Nie
 * @date 2022-07-24
 */
@Data
@Configuration
public class JavaConfigBean {

    @Value("${timeout:20}")
    private int timeout;

    @Value("${newKey:'hello'}")
    private String newKey;

}
