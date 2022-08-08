package org.willingoxjin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Jin.Nie
 * @date 2022-01-07
 */
@FeignClient(value = "eureka-client")
public interface IService {

    @GetMapping("sayHi")
    String sayHi();

}
