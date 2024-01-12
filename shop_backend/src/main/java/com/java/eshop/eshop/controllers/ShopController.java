package com.java.eshop.eshop.controllers;

import com.java.eshop.eshop.dto.ProductStockDTO;
import com.java.eshop.eshop.dto.ResponseDTO;
import com.java.eshop.eshop.dto.ShopDTO;
import com.java.eshop.eshop.dto.ShopProductDTO;
import com.java.eshop.eshop.model.ShopEntity;
import com.java.eshop.eshop.model.ShopProductEntity;
import com.java.eshop.eshop.services.ShopProductService;
import com.java.eshop.eshop.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopProductService shopProductService;


    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Servicio para crear una tienda
     */
    @PostMapping
    public ResponseEntity<ResponseDTO<ShopDTO>> createShop(@RequestBody ShopDTO data) {
        return new ResponseEntity<>(ResponseDTO.<ShopDTO>builder()
                .data(shopService.createShop(data))
                .build(), HttpStatus.OK);
    }


    /**
     * Servicio para actualizar una tienda
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ShopDTO>> updateShop(@RequestBody ShopDTO data, @PathVariable Long id) {
        return new ResponseEntity<>(ResponseDTO.<ShopDTO>builder()
                .data(shopService.updateShop(id, data))
                .build(), HttpStatus.OK);
    }

    /**
     * Servicio para encontrar todas las tiendas con los productos
     */
    @GetMapping
    public ResponseEntity<ResponseDTO<List<ShopDTO>>> findAll() {
        passwordEncoder.encode("Password01");
        return new ResponseEntity<>(ResponseDTO.<List<ShopDTO>>builder()
                .data(shopService.findAll())
                .build(), HttpStatus.OK);
    }

    /**
     * Servicio para agregar productos a una tienda
     */
    @PostMapping("/{id}/products")
    public ResponseEntity<ResponseDTO<List<ShopProductDTO>>> addProductsToShop(@RequestBody List<ShopProductDTO> data, @PathVariable Long id) {
        return new ResponseEntity<>(ResponseDTO.<List<ShopProductDTO>>builder()
                .data(shopProductService.addProductsToShop(id, data))
                .build(), HttpStatus.OK);
    }

    /**
     * Servicio para encontrar el stock de una tienda
     */
    @GetMapping("/{id}/stock")
    public ResponseEntity<ResponseDTO<List<ProductStockDTO>>> findStoreStock(@PathVariable Long id) {
        return new ResponseEntity<>(ResponseDTO.<List<ProductStockDTO>>builder()
                .data(shopProductService.findProductStock(id))
                .build(), HttpStatus.OK);
    }

}
