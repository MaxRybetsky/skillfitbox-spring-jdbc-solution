package ru.skillbox.skillfitbox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.skillfitbox.entity.TrainerStatus;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDetailDto {
    
    private UUID id;
    private String surname;
    private String name;
    private String patronymic;
    private String phone;
    private TrainerStatusDto status;
    private List<String> clientNames;
}
