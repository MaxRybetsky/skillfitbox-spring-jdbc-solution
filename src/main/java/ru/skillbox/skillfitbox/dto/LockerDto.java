package ru.skillbox.skillfitbox.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LockerDto {
    private UUID id;
    private Integer number;
    private String clientFullName;
}
