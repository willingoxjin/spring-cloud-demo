package org.willingoxjin.conroller;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import feign.Feign;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.willingoxjin.api.HystrixDemoService;

/**
 * @author Jin.Nie
 * @date 2022-01-18
 */
@RestController
public class HystrixDemoController {

    @Autowired
    private HystrixDemoService hystrixDemoService;

    @GetMapping("fallback")
    public String fallback() {
        return hystrixDemoService.simulationError();
    }

    @GetMapping("timeout")
    public String timeout(Integer time) {
        return hystrixDemoService.timeoutRetry(time);
    }

    @GetMapping("requestCache")
    public String requestCache(String name) {
        @Cleanup
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        return name;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        // String timeoutRetry = Feign.configKey(HystrixDemoService.class, HystrixDemoService.class.getMethod("timeoutRetry", Integer.class));
        // System.out.println(timeoutRetry);

        String userHome = System.getProperty("user.home");
        System.out.println(userHome);
    }

}
