package org.willingoxjin.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.willingoxjin.api.HystrixDemoService;

import java.util.Map;

/**
 * @author Jin.Nie
 * @date 2022-01-18
 */
@Slf4j
@Component
public class HystrixDemoServiceFallback implements HystrixDemoService {

    @Override
    public String sayHi(@RequestParam(name = "name", required = false) String name) {
        return name;
    }

    @Override
    public Map<String, String> sayHiPost(@RequestBody Map<String, String> map) {
        return map;
    }

    @Override
    public String simulationError() {
        log.info("Fallback: I'm not a black sheep any more");
        return "Fallback: I'm not a black sheep any more";
    }

    @Override
    public String timeoutRetry(@RequestParam(name = "time", required = false) Integer time) {
        return "You are late !";
    }

    @Override
    public String hello(String name) {
        return name;
    }

    @HystrixCommand(
        fallbackMethod = "fallback2",
        commandProperties = {
            // 超时配置
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
        }
    )
    @Override
    public String fallbackTest() {
        log.info("first fallback");
        throw new RuntimeException("first fallback");
    }

    @HystrixCommand(fallbackMethod = "fallback3")
    public String fallback2() {
        log.info("second fallback");
        throw new RuntimeException("second fallback");
    }

    public String fallback3() {
        log.info("third fallback");
        return "third fallback";
    }

}
