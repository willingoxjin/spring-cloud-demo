package org.willingoxjin.dubbo.service;

import org.willingoxjin.dubbo.model.Product;

/**
 * @author Jin.Nie
 * @date 2022-07-31
 */
public interface IProductService {

    /**
     * 发布商品
     */
    Product publish(Product product);

}
