package org.willingoxjin.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author Jin.Nie
 * @date 2022-02-25
 */
@ToString
@Data
@Builder
public class Product {

    // ID
    private Long productId;

    // 商品名
    private String productName;

    // 库存
    private Long stock;

}
