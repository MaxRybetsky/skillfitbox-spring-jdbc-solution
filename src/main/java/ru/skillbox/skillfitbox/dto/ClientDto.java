package ru.skillbox.skillfitbox.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    
    private UUID id;
    
    @NotBlank(message = "Фамилия не может быть пустой")
    private String surname;
    
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    
    private String patronymic;
    
    @NotNull(message = "Дата рождения обязательна")
    @Past(message = "Дата рождения должна быть в прошлом")
    private LocalDate birthday;
    
    @NotBlank(message = "Телефон не может быть пустым")
    private String phone;
    
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Неверный формат email")
    private String email;
    
    private Boolean isActive;
}
