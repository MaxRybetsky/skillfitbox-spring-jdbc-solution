package ru.skillbox.skillfitbox.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.skillbox.skillfitbox.dto.LockerDto;
import ru.skillbox.skillfitbox.entity.Client;
import ru.skillbox.skillfitbox.entity.Locker;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface LockerMapper {
    @Mapping(target = "clientFullName", source = "locker", qualifiedByName = "mapClientFullName")
    LockerDto toDto(Locker locker);

    @Named("mapClientFullName")
    default String mapClientFullName(Locker locker) {
        Client client = locker.getClient();

        if (client == null) {
            return null;
        }

        return String.join(" ", client.getSurname(), client.getName(), client.getPatronymic());
    }
}
