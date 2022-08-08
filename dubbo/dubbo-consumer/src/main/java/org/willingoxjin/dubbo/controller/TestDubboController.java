package org.willingoxjin.dubbo.controller;

import java.math.BigDecimal;
import org.apache.dubbo.common.constants.LoadbalanceRules;
import org.apache.dubbo.config.annotation.DubboReference;
// import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.willingoxjin.dubbo.model.Product;
import org.willingoxjin.dubbo.service.IProductService;
/**
 * @author Jin.Nie
 * @date 2022-08-01
 */
@RestController
public class TestDubboController {

    @DubboReference(loadbalance = LoadbalanceRules.ROUND_ROBIN)
    // @Reference(loadbalance ="roundrobin")
    private IProductService productService;

    @PostMapping("/publish")
    public Product publish(@RequestParam String name,
                        @RequestParam BigDecimal price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return productService.publish(product);
    }

}
