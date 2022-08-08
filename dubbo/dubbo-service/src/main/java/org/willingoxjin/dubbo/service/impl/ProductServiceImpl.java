package org.willingoxjin.dubbo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.LoadbalanceRules;
import org.apache.dubbo.config.annotation.DubboService;
// import org.apache.dubbo.config.annotation.Service;
import org.willingoxjin.dubbo.model.Product;
import org.willingoxjin.dubbo.service.IProductService;

/**
 * @author Jin.Nie
 * @date 2022-07-31
 */
@Slf4j
@DubboService(loadbalance = LoadbalanceRules.ROUND_ROBIN)
// @Service
public class ProductServiceImpl implements IProductService {
    @Override
    public Product publish(Product product) {
        log.info("Publishing prod {}", product.getName());
        return product;
    }

}
