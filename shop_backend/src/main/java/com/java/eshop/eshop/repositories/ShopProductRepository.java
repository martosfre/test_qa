package com.java.eshop.eshop.repositories;

import com.java.eshop.eshop.model.ShopEntity;
import com.java.eshop.eshop.model.ShopProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopProductRepository extends JpaRepository<ShopProductEntity, Long> {

    List<ShopProductEntity> findByShop(ShopEntity shop);

    Optional<ShopProductEntity> findByIdAndShop(Long id, ShopEntity shop);
}
