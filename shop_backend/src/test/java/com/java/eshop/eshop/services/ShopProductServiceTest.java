package com.java.eshop.eshop.services;

import com.java.eshop.eshop.common.ShopException;
import com.java.eshop.eshop.dto.ShopProductDTO;
import com.java.eshop.eshop.model.ShopEntity;
import com.java.eshop.eshop.model.ShopProductEntity;
import com.java.eshop.eshop.repositories.ShopProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ShopProductServiceTest {

    @Mock
    private ShopService shopService;

    @InjectMocks
    private ShopProductService mShopProductService;

    @Mock
    private ShopProductRepository shopProductRepository;

    @Autowired
    private ShopProductService shopProductService;

    @Test
    public void Should_ThrowException_When_ProductDoesNotExist() {
        assertThrows(ShopException.class, () ->
                shopProductService.findShopProductEntityById(200L)
        );
    }

    @Test
    public void Should_ThrowException_When_ShopDoesNotExist() {
        assertThrows(ShopException.class, () ->
                shopProductService.addProductsToShop(200L, new ArrayList<>())
        );
    }

    @Test
    public void Should_ReturnProductListWithId_When_ShopExists() {
        when(shopService.findShopEntityById(1L)).thenReturn(ShopEntity.builder().id(1L).build());
        List<ShopProductDTO> productsList = new ArrayList<>();
        ShopProductDTO testProduct1 = new ShopProductDTO();
        testProduct1.setName("testProduct1");
        testProduct1.setCost(10.25);
        testProduct1.setStock(5);
        productsList.add(testProduct1);
        when(shopProductRepository.save(any())).thenReturn(ShopProductEntity.builder()
                .id(1L)
                .name("testProduct1")
                .cost(10.25)
                .stock(5)
                .build()
        );
        List<ShopProductDTO> returnList = mShopProductService.addProductsToShop(1L, productsList);
        assertAll(
                () -> assertEquals(returnList.stream().findFirst().get().getId(), 1),
                () -> assertEquals(returnList.stream().findFirst().get().getName(), "testProduct1"),
                () -> assertEquals(returnList.stream().findFirst().get().getCost(), 10.25),
                () -> assertEquals(returnList.stream().findFirst().get().getStock(), 5)
        );
    }

    @Test
    public void Should_UpdateProductFields_When_ProductSend() {
        ShopProductEntity shopProductEntity = ShopProductEntity.builder()
                .id(1L)
                .name("testProduct1")
                .cost(10.25)
                .stock(5)
                .build();
        when(shopProductRepository.findById(1L))
                .thenReturn(Optional.of(shopProductEntity));
        ShopProductDTO testProduct2 = new ShopProductDTO();
        testProduct2.setName("testProduct2");
        testProduct2.setStock(7);

        shopProductEntity.setName("testProduct2");
        shopProductEntity.setStock(7);
        when(shopProductRepository.save(shopProductEntity)).thenReturn(shopProductEntity);
        ShopProductDTO response = mShopProductService.updateShopProduct(1L, testProduct2);
        assertAll(
                () -> assertEquals(response.getId(), 1L),
                () -> assertEquals(response.getName(), "testProduct2"),
                () -> assertEquals(response.getCost(), 10.25),
                () -> assertEquals(response.getStock(), 7)
        );

    }
}
