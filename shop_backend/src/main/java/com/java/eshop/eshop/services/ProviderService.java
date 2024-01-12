package com.java.eshop.eshop.services;

import com.java.eshop.eshop.dto.ProviderDTO;
import com.java.eshop.eshop.mapper.ProviderMapper;
import com.java.eshop.eshop.model.ProviderEntity;
import com.java.eshop.eshop.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    @Transactional
    public ProviderDTO createProvider(ProviderDTO provider){
        Optional<ProviderEntity> providerEntity = providerRepository
                .findByDocumentNumber(provider.getDocumentNumber());
        if(providerEntity.isPresent()){
            providerEntity.get().setName(provider.getName());
            return ProviderMapper.INSTANCE.providerEntityToProviderDto(providerRepository
                    .save(providerEntity.get()));
        }
        return ProviderMapper.INSTANCE.providerEntityToProviderDto(providerRepository
                .save(ProviderMapper.INSTANCE.providerDtoToProviderEntity(provider)));
    }

    public List<ProviderDTO> findAll(){
        return providerRepository.findAll().stream()
                .map(ProviderMapper.INSTANCE::providerEntityToProviderDto)
                .toList();
    }

}
