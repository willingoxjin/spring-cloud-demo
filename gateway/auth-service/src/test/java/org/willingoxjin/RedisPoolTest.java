package org.willingoxjin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jin.Nie
 * @date 2022-03-14
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RedisPoolTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Test
    void testRedisPool() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(16);

        AtomicInteger integer = new AtomicInteger(0);
        while (true) {
            service.submit(() -> {
                integer.incrementAndGet();
                redisTemplate.opsForValue().set("a" + integer, "b");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

    }


}
