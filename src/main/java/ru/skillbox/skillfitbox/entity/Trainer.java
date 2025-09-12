package ru.skillbox.skillfitbox.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trainer {
    
    private UUID id;
    private String surname;
    private String name;
    private String patronymic;
    private String phone;
    private TrainerStatus status;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    private List<Client> clients;
}
