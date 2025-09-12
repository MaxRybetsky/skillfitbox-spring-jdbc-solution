package ru.skillbox.skillfitbox.repository;

import ru.skillbox.skillfitbox.entity.Trainer;

import java.util.List;
import java.util.UUID;

public interface TrainerRepository {
    
    Trainer save(Trainer trainer);
    
    Trainer update(Trainer trainer);
    
    Trainer findById(UUID id);
    
    List<Trainer> findAll();
}