package com.java.eshop.eshop.mapper;

import com.java.eshop.eshop.dto.ProductStockDTO;
import com.java.eshop.eshop.dto.ShopProductDTO;
import com.java.eshop.eshop.model.ShopProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopProductMapper {

    ShopProductMapper INSTANCE = Mappers.getMapper(ShopProductMapper.class);

    ShopProductDTO shopProductEntToShopProductDto(ShopProductEntity shopProduct);

    ProductStockDTO shopProductEntToProductStockDTO(ShopProductEntity shopProduct);

}
