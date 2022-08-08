package org.willingoxjin;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.willingoxjin.model.AuthUser;

import java.util.Date;

/**
 * @author Jin.Nie
 * @date 2022-03-04
 */
@Slf4j
@Service
public class JwtService {

    /**  用户id */
    private static final String USER_ID = "userId";

    /** 随机加密串 name */
    private static final String SECURITY = "security";

    // FIXME 生产环境 KEY和ISSUER 必须加密好之后放在配置中心，从配置中心读取

    /** 加密的 KEY */
    @Value("${jwt-auth.key}")
    private String key = "william";

    /** issuer 发行方 */
    @Value("${jwt-auth.issuer}")
    private String issuer = "willingoxjin";

    /** 随机加密串 */
    @Value("${jwt-auth.security}")
    private String security;

    @Value("${jwt-auth.expired-mills}")
    private Long expiredMills;

    /**
     * 生成 token
     * @return token
     */
    public String generateToken(AuthUser authUser, long tokenExpTime) {
        Date now = new Date();
        // 加密算法，在使用时可以自行修改算法
        Algorithm algorithm = Algorithm.HMAC384(key);
        // 生成 token
        String token = JWT.create()
                .withIssuer(issuer)   /* issuer 发行方 */
                .withIssuedAt(now)    /* key 生成的时间 */
                .withExpiresAt(new Date(now.getTime() + tokenExpTime))    /* token过期时间 */
                .withClaim(USER_ID, authUser.getUserId())   /* claim 自定义需要校检的其他属性 */
                .withClaim(SECURITY, security)
                .sign(algorithm);      /* 使用的加密对象 */

        log.info("jwt generated userId={}", authUser.getUserId());
        return token;
    }

    public String generateToken(AuthUser authUser) {
        return generateToken(authUser, expiredMills);
    }

    /**
     * 校验 token 是否为当前用户，是否过期
     * 实际业务可根据需求添加鉴权的字段，如用户id，用户微信码......
     * @param token token
     * @param userId 用户Id
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
