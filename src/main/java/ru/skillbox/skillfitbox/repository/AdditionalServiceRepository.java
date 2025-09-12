package ru.skillbox.skillfitbox.repository;

import ru.skillbox.skillfitbox.entity.AdditionalService;

import java.util.List;
import java.util.UUID;

public interface AdditionalServiceRepository {
    
    AdditionalService findById(String id);
    
    List<AdditionalService> findAll();
    
    void addServiceToClient(UUID clientId, String serviceId);
    
    List<String> findClientNamesByServiceId(String serviceId);
}