package com.java.eshop.eshop.repositories;

import com.java.eshop.eshop.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findByDocumentNumber(String documentNumber);
}
