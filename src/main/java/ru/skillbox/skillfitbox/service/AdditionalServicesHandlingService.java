package ru.skillbox.skillfitbox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.skillfitbox.dto.ServiceDto;
import ru.skillbox.skillfitbox.entity.AdditionalService;
import ru.skillbox.skillfitbox.mapper.ServiceMapper;
import ru.skillbox.skillfitbox.repository.AdditionalServiceRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервисный класс для бизнес-логики операций с услугами.
 */
@Service
@RequiredArgsConstructor
public class AdditionalServicesHandlingService {

    private final AdditionalServiceRepository additionalServiceRepository;
    private final ServiceMapper serviceMapper;

    /**
     * Получает список всех услуг с именами клиентов.
     * 
     * @return список DTO услуг с именами клиентов
     */
    public List<ServiceDto> getAllServices() {
        List<AdditionalService> additionalServices = additionalServiceRepository.findAll();
        return additionalServices.stream()
                .map(service -> {
                    ServiceDto serviceDto = serviceMapper.toDto(service);
                    List<String> clientNames = additionalServiceRepository.findClientNamesByServiceId(serviceDto.getId());
                    serviceDto.setClientNames(clientNames);
                    return serviceDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Получает информацию о конкретной услуге по ID включая имена клиентов.
     * 
     * @param id ID услуги
     * @return DTO услуги с именами клиентов или null если не найдена
     */
    public ServiceDto getServiceByIdWithClients(String id) {
        AdditionalService additionalService = additionalServiceRepository.findById(id);
        if (additionalService == null) {
            return null;
        }

        ServiceDto serviceDto = serviceMapper.toDto(additionalService);
        List<String> clientNames = additionalServiceRepository.findClientNamesByServiceId(serviceDto.getId());
        serviceDto.setClientNames(clientNames);

        return serviceDto;
    }
}
