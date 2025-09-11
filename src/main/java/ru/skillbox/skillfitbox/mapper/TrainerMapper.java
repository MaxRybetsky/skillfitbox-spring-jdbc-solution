package ru.skillbox.skillfitbox.mapper;

import org.mapstruct.Mapper;
import ru.skillbox.skillfitbox.dto.TrainerDetailDto;
import ru.skillbox.skillfitbox.dto.TrainerDto;
import ru.skillbox.skillfitbox.entity.Trainer;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TrainerMapper {
    
    TrainerDto toDto(Trainer trainer);

    TrainerDetailDto toDetailDto(Trainer trainer, List<String> clientNames);

    Trainer toEntity(TrainerDto trainerDto);
}
