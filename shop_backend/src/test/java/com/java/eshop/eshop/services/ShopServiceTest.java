package com.java.eshop.eshop.services;

import com.java.eshop.eshop.common.ShopException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ShopServiceTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void Should_ThrowException_When_ShopDoesNotExist() {
        assertThrows(ShopException.class, () ->
                shopService.findShopEntityById(200L)
        );
    }
}
