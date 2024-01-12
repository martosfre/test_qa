package com.java.eshop.eshop.controllers;

import com.java.eshop.eshop.dto.ResponseDTO;
import com.java.eshop.eshop.dto.ShopProductDTO;
import com.java.eshop.eshop.services.ShopProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    @Autowired
    private ShopProductService shopProductService;

    /**
     * Servicio para actualizar un producto
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<ShopProductDTO>> updateProduct(@RequestBody ShopProductDTO data, @PathVariable Long id) {
        return new ResponseEntity<>(ResponseDTO.<ShopProductDTO>builder()
                .data(shopProductService.updateShopProduct(id, data))
                .build(), HttpStatus.OK);
    }

}
