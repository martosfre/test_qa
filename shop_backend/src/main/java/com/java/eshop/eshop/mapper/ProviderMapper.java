package com.java.eshop.eshop.mapper;

import com.java.eshop.eshop.dto.ProviderDTO;
import com.java.eshop.eshop.model.ProviderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProviderMapper {

    ProviderMapper INSTANCE = Mappers.getMapper(ProviderMapper.class);

    ProviderEntity providerDtoToProviderEntity(ProviderDTO providerDTO);

    ProviderDTO providerEntityToProviderDto(ProviderEntity provider);

}
