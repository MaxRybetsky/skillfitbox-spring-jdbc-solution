package ru.skillbox.skillfitbox.mapper;

import ru.skillbox.skillfitbox.dto.ClientDto;
import ru.skillbox.skillfitbox.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ClientMapper {
    
    ClientDto toDto(Client client);

    @Mapping(target = "trainer", ignore = true)
    @Mapping(target = "locker", ignore = true)
    @Mapping(target = "services", ignore = true)
    Client toEntity(ClientDto clientDto);
}
