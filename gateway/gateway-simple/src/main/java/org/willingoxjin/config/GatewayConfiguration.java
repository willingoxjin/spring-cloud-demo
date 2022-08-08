package org.willingoxjin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.annotation.Resource;
import java.time.ZonedDateTime;


/**
 * @author Jin.Nie
 * @date 2022-02-23
 */
@Configuration
public class GatewayConfiguration {

    @Resource
    private TimeRecordFilter timeRecordFilter;

    @Resource
    private AuthFilter authFilter;

    @Resource
    private KeyResolver hostNameResolver;

    @Resource(name = "redisLimiterUser")
    private RedisRateLimiter redisRateLimiter;

    // 需要鉴权的路由列表
    @Value("${auth-filter.url-pattens}")
    private String[] authFilterUrls;

    /**
     * 手动配置路由规则
     */
    @Bean
    @Order  // bean 的加载顺序
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(this::javaFeignclientRoute)
                .route("seckill", this::seckillRoute)
                .route("authTest", this::authTestRoute)
                .route("rateLimitTest", this::rateLimitTestRoute)
                .build();
    }

    /**
     * /java-feignclient/**"
     */
    private Route.AsyncBuilder javaFeignclientRoute(PredicateSpec r) {
        return r.path("/java-feignclient/**") // 匹配的规则
                .and().method(HttpMethod.GET)       // get请求
                .and().header("x-token-gateway")    // 请求中必须有指定 header
                .filters(f -> f.stripPrefix(1)      // 切断第一个前缀：如 http://localhost/feignclient/sayHi -> http://localhost/sayHi
                        .addResponseHeader("x-gateway-param", "hello")
                        .filter(timeRecordFilter)
                )
                .uri("lb://FEIGN-CLIENT");
    }

    /**
     * /seckill/**
     *
     * after 在某个时间之后路由才会生效
     * before 在某个时间之前路由生效，过了该时间点失效
     * between 在时间之间 after+before
     */
    private Route.AsyncBuilder seckillRoute(PredicateSpec r) {
        return r.path("/seckill/**")
                .and().after(ZonedDateTime.now().plusMinutes(1))    // 一分钟后路由生效
                // .and().before()
                // .and().between()
                .filters(f -> f.stripPrefix(1))
                .uri("lb://FEIGN-CLIENT");
    }


    private Route.AsyncBuilder authTestRoute(PredicateSpec r) {
        // FIXME: 可以将需要过滤的路由，放到配置中心，动态拉取需要匹配的路由列表
        return r.path(authFilterUrls)
                .filters(f -> f.stripPrefix(1)  // 切断第一个前缀：如 http://localhost/feignclient/sayHi -> http://localhost/sayHi
                        .filter(authFilter)     // 鉴权过滤器
                )
                .uri("lb://FEIGN-CLIENT");
    }

    /**
     * rateLimitTest
     */
    private Route.AsyncBuilder rateLimitTestRoute(PredicateSpec r) {
        return r.path("/rateLimitTest/**")
                .filters(f -> f.stripPrefix(1)  // 切断第一个前缀：如 http://localhost/feignclient/sayHi -> http://localhost/sayHi
                        .requestRateLimiter(c -> {
                            c.setKeyResolver(hostNameResolver);
                            c.setRateLimiter(redisRateLimiter);
                            c.setStatusCode(HttpStatus.BAD_GATEWAY);
                        })    // 限流过滤器
                )
                .uri("lb://FEIGN-CLIENT");
    }

}
