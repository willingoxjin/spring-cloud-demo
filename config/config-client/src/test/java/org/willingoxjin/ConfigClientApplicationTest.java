package org.willingoxjin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author Jin.Nie
 * @date 2022-02-14
 */

@SpringBootTest
public class ConfigClientApplicationTest {

    @Value("${name}")
    private String name;

    @Value("${myMotto}")
    private String motto;

    @Value("${hi}")
    private String hi;

    @Value("${test}")
    private String test;

    @Test
    public void configTest() {
        System.out.println(name);
        System.out.println(motto);
        System.out.println(hi);
        System.out.println(test);
    }

}
