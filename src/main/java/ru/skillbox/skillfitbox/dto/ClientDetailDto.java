package ru.skillbox.skillfitbox.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDetailDto {
    
    private UUID id;
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthday;
    private String phone;
    private String email;
    private Boolean isActive;
    private LockerDto locker;
    private TrainerDto trainer;
    private List<String> services;
}
