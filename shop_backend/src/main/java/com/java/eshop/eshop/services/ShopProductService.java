package com.java.eshop.eshop.services;

import com.java.eshop.eshop.common.ShopException;
import com.java.eshop.eshop.dto.ProductStockDTO;
import com.java.eshop.eshop.dto.ShopProductDTO;
import com.java.eshop.eshop.mapper.ShopProductMapper;
import com.java.eshop.eshop.model.ShopEntity;
import com.java.eshop.eshop.model.ShopProductEntity;
import com.java.eshop.eshop.repositories.ShopProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopProductService {

    @Autowired
    private ShopProductRepository shopProductRepository;

    @Autowired
    private ShopService shopService;

    /**
     * Metodo para agregar productos a la tienda
     * @param shopId
     * @param productsList
     * @return
     */
    public List<ShopProductDTO> addProductsToShop(Long shopId, List<ShopProductDTO> productsList) {
        // 1. Busco si existe shop
        shopService.findShopEntityById(shopId);
        // 2. Creo los productos
        return productsList.stream().map(product -> {
                    ShopProductEntity newProduct = shopProductRepository.save(ShopProductEntity.builder()
                            .name(product.getName())
                            .cost(product.getCost())
                            .stock(product.getStock())
                            .shop(ShopEntity.builder().id(shopId).build())
                            .build());
                    return ShopProductMapper.INSTANCE.shopProductEntToShopProductDto(newProduct);
                }
        ).toList();
    }

    /**
     * Metodo para actualizar un producto
     * @param productId
     * @param product
     * @return
     */
    public ShopProductDTO updateShopProduct(Long productId, ShopProductDTO product){
        // 1. Busco el producto
        ShopProductEntity productEntity = findShopProductEntityById(productId);
        productEntity.setName(product.getName() == null ? productEntity.getName() : product.getName());
        productEntity.setCost(product.getCost() == null ? productEntity.getCost() : product.getCost());
        productEntity.setStock(product.getStock() == null ? productEntity.getStock() : product.getStock());
        // 2. Actualizo el producto
        return ShopProductMapper.INSTANCE.shopProductEntToShopProductDto(shopProductRepository.save(productEntity));

    }

    /**
     * Metodo para encontrar si existe producto en la tienda
     * @param productId
     * @return
     */
    public ShopProductEntity findShopProductEntityById(Long productId){
        return shopProductRepository.findById(productId)
                .orElseThrow(() -> new ShopException("Product with id " + productId + " doesn't exist"));
    }

    /**
     * Metodo para encontrar stock de los productos de la tienda
     * @param shopId
     * @return
     */
    public List<ProductStockDTO> findProductStock(Long shopId){
        // 1. Busco si existe shop
        shopService.findShopEntityById(shopId);
        // 2. Busco stock
        return shopProductRepository.findByShop(ShopEntity.builder().id(shopId).build())
                .stream().map(ShopProductMapper.INSTANCE::shopProductEntToProductStockDTO)
                .toList();
    }
}
