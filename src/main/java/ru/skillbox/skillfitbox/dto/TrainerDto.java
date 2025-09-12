package ru.skillbox.skillfitbox.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDto {
    
    private UUID id;
    
    @NotBlank(message = "Фамилия не может быть пустой")
    private String surname;
    
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    
    private String patronymic;
    
    @NotBlank(message = "Телефон не может быть пустым")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Неверный формат телефона")
    private String phone;
    
    @NotNull(message = "Статус тренера обязателен")
    private TrainerStatusDto status;
}
