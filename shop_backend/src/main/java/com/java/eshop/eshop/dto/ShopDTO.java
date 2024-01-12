package com.java.eshop.eshop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopDTO {

    private Long id;
    private String name;
    private String address;
    private List<ShopProductDTO> productsList;

}
