package ru.skillbox.skillfitbox.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private UUID id;
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthday;
    private String phone;
    private String email;
    private Boolean isActive;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    private Trainer trainer;
    private Locker locker;
    private List<AdditionalService> services;
}
