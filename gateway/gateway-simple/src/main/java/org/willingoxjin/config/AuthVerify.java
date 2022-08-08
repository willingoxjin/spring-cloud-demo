package org.willingoxjin.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Jin.Nie
 * @date 2022-03-15
 */
@Slf4j
@Component
public class AuthVerify {

    /**  用户id */
    private static final String USER_ID = "userId";

    /** 随机加密串 name */
    private static final String SECURITY = "security";

    // FIXME 生产环境 key、issuer、security 必须加密好之后放在配置中心，从配置中心读取

    /** 加密的 KEY */
    @Value("${jwt-auth.key}")
    private String key = "william";

    /** issuer 发行方 */
    @Value("${jwt-auth.issuer}")
    private String issuer = "willingoxjin";

    /** 随机加密串 */
    @Value("${jwt-auth.security}")
    private String security;

    /**
     * 校验 token 是否为当前用户，是否过期
     * 实际业务可根据需求添加鉴权的字段，如用户id，用户微信码......
     * @param token token
     * @param userId 用户id
     * @return 是否通过验证
     */
    public boolean verify(String token, Long userId) {
        log.info("verifying jwt - username={}", userId);
        try {
            Algorithm algorithm = Algorithm.HMAC384(key);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .withClaim(USER_ID, userId)
                    .withClaim(SECURITY, security)
                    .build();
            // 验证出现问题时，会抛出异常
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            log.error("auth failed", e);
            return false;
        }

    }

}
