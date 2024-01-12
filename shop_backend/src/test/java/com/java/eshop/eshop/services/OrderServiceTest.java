package com.java.eshop.eshop.services;

import com.java.eshop.eshop.common.ShopException;
import com.java.eshop.eshop.dto.OrderDTO;
import com.java.eshop.eshop.dto.OrderProductDTO;
import com.java.eshop.eshop.dto.OrderShopDTO;
import com.java.eshop.eshop.model.ShopEntity;
import com.java.eshop.eshop.model.ShopProductEntity;
import com.java.eshop.eshop.repositories.OrderRepository;
import com.java.eshop.eshop.repositories.ShopProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTest {

    @Mock
    private ShopService shopService;

    @Mock
    private ShopProductRepository shopProductRepository;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    private static OrderDTO orderDTO;

    @BeforeEach
    private void createOrder(){
        orderDTO = new OrderDTO();
        orderDTO.setClientId(1L);
        orderDTO.setTotalCost(200.0);
        OrderShopDTO orderShopDTO = new OrderShopDTO();
        orderShopDTO.setShopId(1L);
        OrderProductDTO orderProductDTO = new OrderProductDTO();
        orderProductDTO.setProductId(1L);
        orderProductDTO.setQuantity(1);
        orderProductDTO.setUnitCost(12.25);

        orderShopDTO.setProductsList(List.of(orderProductDTO));
        orderDTO.setShopList(List.of(orderShopDTO));
    }

    @Test
    public void Should_ThrowException_When_ProductDoesNotExistInShop(){
        when(shopService.findShopEntityById(1L)).thenReturn(ShopEntity.builder().id(1L).build());
        when(shopProductRepository.findByIdAndShop(anyLong(), any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ShopException.class, () -> orderService.verifyShopAndProductExistance(orderDTO));
    }

    @Test
    public void Should_ThrowException_When_ProductDoesNotHaveStock(){
        when(shopService.findShopEntityById(1L)).thenReturn(ShopEntity.builder().id(1L).build());
        when(shopProductRepository.findByIdAndShop(1L, ShopEntity.builder().id(1L).build()))
                .thenReturn(Optional.of(ShopProductEntity.builder().id(1L).stock(0).build()));

        Assertions.assertThrows(ShopException.class, () -> orderService.verifyShopAndProductExistance(orderDTO));
    }

    @Test
    public void Should_ThrowException_When_ProductDoesNotExist(){
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ShopException.class, () -> orderService.deleteOrder(1L));
    }

}
