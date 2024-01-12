package com.java.eshop.eshop.repositories;

import com.java.eshop.eshop.model.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Long> {

    Optional<ShopEntity> findByName(String name);

}
