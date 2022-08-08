package org.willingoxjin.service;

/**
 * @author Jin.Nie
 * @date 2022-01-21
 */

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.willingoxjin.api.HystrixDemoService;

@Slf4j
@Service
public class RequestCacheTestService {

    @Autowired
    private HystrixDemoService hystrixDemoService;

    @CacheResult
    @HystrixCommand(commandKey = "cacheKKey")
    public String requestCacheTest(@CacheKey String name) {
        log.info("requestCacheTest start name={}", name);
        String s = hystrixDemoService.hello(name);
        log.info("after requestCacheTest start name={}", name);
        return s;
    }

}
