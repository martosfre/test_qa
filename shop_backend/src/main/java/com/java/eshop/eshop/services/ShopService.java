package com.java.eshop.eshop.services;

import com.java.eshop.eshop.common.ShopException;
import com.java.eshop.eshop.dto.ShopDTO;
import com.java.eshop.eshop.mapper.ShopMapper;
import com.java.eshop.eshop.model.ShopEntity;
import com.java.eshop.eshop.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    /**
     * Metodo para encontrar todas las tiendas
     * @return
     */
    public List<ShopDTO> findAll() {
        return shopRepository.findAll()
                .stream().map(ShopMapper.INSTANCE::shopEntToShopDto)
                .toList();
    }

    /**
     * Metodo para crear una tienda
     * @param data
     * @return
     */
    @Transactional
    public ShopDTO createShop(ShopDTO data) {
        return ShopMapper.INSTANCE.shopEntToShopDto(shopRepository.save(ShopEntity.builder()
                .name(data.getName())
                .address(data.getAddress())
                .build()));
    }

    /**
     * Metodo para actualizar tienda
     * @param shopId
     * @param data
     * @return
     */
    public ShopDTO updateShop(Long shopId, ShopDTO data) {
        // 1. Busco si existe shop
        ShopEntity shop = findShopEntityById(shopId);
        // 2. Seteo los datos nuevos
        shop.setName(data.getName());
        shop.setAddress(data.getAddress());
        // 3. Actualizo
        return ShopMapper.INSTANCE.shopEntToShopDto(shopRepository.save(shop));
    }

    /**
     * Metodo para obtener una tienda por id
     * @param shopId
     * @return
     */
    public ShopEntity findShopEntityById(Long shopId){
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException("Shop with id " + shopId + " doesn't exist"));
    }

}
