package org.willingoxjin.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.willingoxjin.hystrix.HystrixDemoServiceFallback;
/**
 * @author Jin.Nie
 * @date 2022-01-18
 */

@FeignClient(value = "feign-client", fallback = HystrixDemoServiceFallback.class)
public interface HystrixDemoService extends IService {
}
