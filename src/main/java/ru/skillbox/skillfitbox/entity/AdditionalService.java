package ru.skillbox.skillfitbox.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalService {
    
    private String id;
    private String name;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    private List<Client> clients;
}
