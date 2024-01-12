package com.java.eshop.eshop.mapper;

import com.java.eshop.eshop.dto.ShopDTO;
import com.java.eshop.eshop.model.ShopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopMapper {

    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);


    ShopDTO shopEntToShopDto(ShopEntity shop);

}
