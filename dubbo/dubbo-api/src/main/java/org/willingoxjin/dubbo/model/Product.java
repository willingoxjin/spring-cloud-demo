package org.willingoxjin.dubbo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author Jin.Nie
 * @date 2022-07-31
 */
@Data
public class Product implements Serializable {

    private String name;

    private BigDecimal price;


}
