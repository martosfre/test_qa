package com.java.eshop.eshop.mapper;

import com.java.eshop.eshop.dto.ClientDTO;
import com.java.eshop.eshop.model.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDTO clientEntToClientDto(ClientEntity clientEntity);

    ClientEntity personDtoToPersonEnt(ClientDTO clientDTO);

}
