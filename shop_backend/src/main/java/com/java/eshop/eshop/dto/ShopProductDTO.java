package com.java.eshop.eshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopProductDTO {

    private Long id;
    private String name;
    private Double cost;
    private Integer stock;

}
