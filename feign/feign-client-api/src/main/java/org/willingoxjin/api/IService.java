package org.willingoxjin.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Jin.Nie
 * @date 2022-01-14
 */
@FeignClient(value = "feign-client", primary = false)
public interface IService {

    @GetMapping("/sayHi")
    String sayHi(@RequestParam(name = "name", required = false) String name);

    @PostMapping("/sayHi")
    Map<String, String> sayHiPost(@RequestBody Map<String, String> map);

    @GetMapping("simulationError")
    String simulationError();

    @GetMapping("timeoutRetry")
    String timeoutRetry(@RequestParam("time") Integer time);

    @GetMapping("hello")
    String hello(@RequestParam(name = "name", required = false) String name);

    @GetMapping("fallbackTest")
    String fallbackTest();

}
