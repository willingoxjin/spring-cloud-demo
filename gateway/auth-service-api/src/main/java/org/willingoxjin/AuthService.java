package org.willingoxjin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.willingoxjin.model.request.LoginRequest;
import org.willingoxjin.model.request.RefreshTokenRequest;
import org.willingoxjin.model.request.VerifyRequest;
import org.willingoxjin.model.response.AuthResponse;

/**
 * @author Jin.Nie
 * @date 2022-03-03
 */
@FeignClient(value = "auth-service")
public interface AuthService {

    @PostMapping("/login")
    @ResponseBody
    AuthResponse login(LoginRequest loginRequest);

    @PostMapping("/verify")
    @ResponseBody
    AuthResponse verify(VerifyRequest verifyRequest);

    @PostMapping("/refreshToken")
    @ResponseBody
    AuthResponse refreshToken(RefreshTokenRequest request);

}
