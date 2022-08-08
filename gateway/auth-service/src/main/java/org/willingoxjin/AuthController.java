package org.willingoxjin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.willingoxjin.model.AuthUser;
import org.willingoxjin.model.request.LoginRequest;
import org.willingoxjin.model.request.RefreshTokenRequest;
import org.willingoxjin.model.request.VerifyRequest;
import org.willingoxjin.model.response.AuthResponse;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.willingoxjin.model.response.AuthResponseCode.*;

/**
 * @author Jin.Nie
 * @date 2022-03-03
 */
@Slf4j
@RestController
public class AuthController implements AuthService {

    // token 过期时间
    @Value("${jwt-auth.expired-mills}")
    private Long expiredMills;

    @Resource
    private JwtService jwtService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 登录获取token
     */
    @PostMapping("/login")
    @ResponseBody
    @Override
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        AuthUser user = AuthUser.builder()
                .userId(loginRequest.getUserId())
                .build();

        // TODO 验证账号密码，这里由于演示原因，省略验证逻辑

        // 生成token
        String token = jwtService.generateToken(user, expiredMills);
        user.setToken(token);
        user.setRefreshToken(UUID.randomUUID().toString());

        // save to redis
        redisTemplate.opsForValue().set(user.getRefreshToken(), user, expiredMills + 1000L, TimeUnit.MILLISECONDS);

        return AuthResponse.builder()
                .code(SUCCESS)
                .authUser(user)
                .build();
    }

    /**
     * 验证token是否正确
     */
    @PostMapping("/verify")
    @ResponseBody
    @Override
    public AuthResponse verify(@RequestBody VerifyRequest verifyRequest) {
        String token = verifyRequest.getToken();
        Long userId = verifyRequest.getUserId();
        boolean success = jwtService.verify(token, userId);
        return AuthResponse.builder()
                // TODO 此处最好用invalid token之类的错误信息
                .code(success ? SUCCESS : INCORRECT_USER_OR_PWD)
                .build();
    }

    /**
     * 刷新token
     */
    @PostMapping("/refreshToken")
    @ResponseBody
    @Override
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        AuthUser user = (AuthUser) redisTemplate.opsForValue().get(refreshToken);
        if (user == null) {
            return AuthResponse.builder()
                    .code(INCORRECT_USER_OR_PWD)
                    .build();
        }

        String token = jwtService.generateToken(user, expiredMills);
        user.setToken(token);
        user.setRefreshToken(UUID.randomUUID().toString());

        // update to redis
        redisTemplate.delete(refreshToken);
        redisTemplate.opsForValue().set(user.getRefreshToken(), user, expiredMills + 1000L, TimeUnit.MILLISECONDS);

        return AuthResponse.builder()
                .code(SUCCESS)
                .authUser(user)
                .build();
    }

}
