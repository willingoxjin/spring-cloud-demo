package org.willingoxjin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.willingoxjin.model.Product;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jin.Nie
 * @date 2022-02-25
 */
@Slf4j
@RestController
@RequestMapping("killProduct")
public class KillProductController {

    private static final Map<Long, Product> items = new ConcurrentHashMap<>();

    static {
        // 模拟初始化商品库存
        Long id1 = 1L;
        Product product1 = Product.builder()
                .productId(id1)
                .productName("辣椒炒肉")
                .stock(100L)
                .build();
        items.putIfAbsent(id1, product1);

        Long id2 = 2L;
        Product product2 = Product.builder()
                .productId(id1)
                .productName("炸酱面")
                .stock(120L)
                .build();
        items.putIfAbsent(id2, product2);

        Long id3 = 3L;
        Product product3 = Product.builder()
                .productId(id1)
                .productName("水蒸蛋")
                .stock(30L)
                .build();
        items.putIfAbsent(id3, product3);
    }

    @GetMapping(value = "detail/{pid}")
    public Product getDetail(@PathVariable Long pid) {
        return items.get(pid);
    }

    @PostMapping("placeOrder/{pid}")
    public String placeOrder(@PathVariable Long pid) {
        Product product = items.get(pid);
        if (product==null) {
            return "Product not found";
        } else if (product.getStock() <= 0L) {
            return "Stock out";
        }

        synchronized (product) {
            if (product.getStock() <= 0L) {
                return "Stock out";
            }
            product.setStock(product.getStock() -1L);
            log.info("product decr: {}", product);
        }

        return "Place the order successfully";
    }

}
