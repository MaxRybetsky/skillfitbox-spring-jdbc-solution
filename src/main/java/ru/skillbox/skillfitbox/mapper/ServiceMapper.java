package ru.skillbox.skillfitbox.mapper;

import org.mapstruct.Mapper;
import ru.skillbox.skillfitbox.dto.ServiceDto;
import ru.skillbox.skillfitbox.entity.AdditionalService;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ServiceMapper {
    ServiceDto toDto(AdditionalService additionalService);
}
