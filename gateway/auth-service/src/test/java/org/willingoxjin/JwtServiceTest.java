package org.willingoxjin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.willingoxjin.model.AuthUser;

/**
 * @author Jin.Nie
 * @date 2022-03-04
 */
@DisplayName("测试JWT")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @DisplayName("测试生成token")
    @ParameterizedTest
    @CsvSource({
        "123456",
        "666666"
    })
    @Timeout(5)
    public void generateTokenTest(ArgumentsAccessor argumentsAccessor) {
        AuthUser authUser = AuthUser.builder()
                .userId(argumentsAccessor.getLong(0))
                .build();
        String token = jwtService.generateToken(authUser, 600000L);
        System.out.println(token);

        System.out.println(jwtService.verify(token, argumentsAccessor.getLong(0)));
    }

    @ParameterizedTest
    @CsvSource({
            "123456, eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJpc3MiOiJ3aWxsaW5nb3hqaW4iLCJzZWN1cml0eUtleSI6IlRuWkJkM24wcXlhYk0yVVAiLCJleHAiOjE2NDczMzU5NjgsImlhdCI6MTY0NzMzNTkwOCwidXNlcklkIjoxMjM0NTZ9.9vmd087_uKANbdQALc4qR4XHVx9O1_8ae_xTzmmk8yz8_ZjEGX7JsajvXmqmi9m1",
            "666666, eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJpc3MiOiJ3aWxsaW5nb3hqaW4iLCJleHAiOjE2NDYzODczNjEsIldpbGxpYW0iOiJsaXNpIiwiaWF0IjoxNjQ2Mzg3MzAxfQ.4U_xC2B08Z97trO4Ix7jUV0TYW_DJPdF0bYkP27vGWajK2oDiEKpFK9bB3mBpRUW",
            "66666688, eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJpc3MiOiJ3aWxsaW5nb3hqaW4iLCJleHAiOjE2NDYzODcwMDEsIldpbGxpYW0iOiJsaXNpIiwiaWF0IjoxNjQ2Mzg2OTQxfQ.4HQbZ52oIMJid6u4Vdx3ST_9LOz5BVYab0l-PPUZ9H2445xybAy78WBXOB3WRbFh"
    })
    public void verifyTest(ArgumentsAccessor argumentsAccessor) {
        System.out.println(jwtService.verify(argumentsAccessor.getString(1), argumentsAccessor.getLong(0)));
    }

}