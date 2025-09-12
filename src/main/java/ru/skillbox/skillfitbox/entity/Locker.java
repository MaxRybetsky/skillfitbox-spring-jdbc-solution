package ru.skillbox.skillfitbox.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Locker {
    
    private UUID id;
    private Integer number;
    private Client client;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;
}
