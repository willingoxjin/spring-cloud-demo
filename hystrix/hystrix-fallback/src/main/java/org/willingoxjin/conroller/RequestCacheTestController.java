package org.willingoxjin.conroller;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.willingoxjin.service.RequestCacheTestService;

/**
 * @author Jin.Nie
 * @date 2022-01-21
 */
@Slf4j
@RestController
public class RequestCacheTestController {

    @Autowired
    private RequestCacheTestService requestCacheTestService;

    @GetMapping("requestCacheTest")
    public String requestCacheTest(String name) {
        @Cleanup
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        log.info("1");
        name = requestCacheTestService.requestCacheTest(name);
        log.info("2");
        name = requestCacheTestService.requestCacheTest(name);
        log.info("3");
        name += "!";
        name = requestCacheTestService.requestCacheTest(name);
        log.info("4");

        return name;
    }


}
