package ru.skillbox.skillfitbox.repository;

import ru.skillbox.skillfitbox.entity.Locker;

import java.util.List;
import java.util.UUID;

public interface LockerRepository {
    
    Locker findById(UUID id);
    
    List<Locker> findAllWithClientInfo();
    
    void update(Locker locker);
}