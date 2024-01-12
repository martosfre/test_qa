package com.java.eshop.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private Long clientId;
    private Long providerId;
    private Double totalCost;
    private List<OrderShopDTO> shopList;

}
