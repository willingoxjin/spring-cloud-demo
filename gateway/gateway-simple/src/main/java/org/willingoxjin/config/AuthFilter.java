package org.willingoxjin.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;


/**
 * @author Jin.Nie
 * @date 2022-03-14
 */
@Slf4j
@Component
public class AuthFilter implements GatewayFilter, Ordered {

    private static final String AUTH = "Authorization";

    private static final String USERID = "w-userid";

    @Resource
    private AuthVerify authVerify;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("gateway auth start");
        ServerHttpRequest request = exchange.getRequest();

        // 是否需要鉴权
        // if (!needFilter(request)) {
        //     return chain.filter(exchange.mutate()
        //             .build());
        // }


        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(AUTH);
        String userId = headers.getFirst(USERID);

        // 鉴权
        ServerHttpResponse response = exchange.getResponse();
        // token is blank
        if (StringUtils.isBlank(token) || StringUtils.isBlank(userId)) {
            log.error("token or userId not found token={}, userId={}", token, userId);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        Long userIdLong = Long.valueOf(userId);
        // 校检 jwt token
        boolean verify = authVerify.verify(token, userIdLong);
        // invalid token
        if (!verify) {
            log.error("invalid token");
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }

        // TODO 将用户信息存放在请求header中传递给下游业务
        ServerHttpRequest.Builder mutate = request.mutate();
        mutate.header(USERID, userId);
        mutate.header(AUTH, token);
        // TODO 如果响应中需要放数据，也可以放在response的header中
        response.getHeaders().add(USERID, userId);
        response.getHeaders().add(AUTH, token);
        response.setStatusCode(HttpStatus.OK);

        // 传递到下一个过滤器
        return chain.filter(exchange.mutate()
                .request(mutate.build())
                .response(response)
                .build());
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
