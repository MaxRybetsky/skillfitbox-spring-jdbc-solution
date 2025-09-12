package ru.skillbox.skillfitbox.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.skillfitbox.dto.ClientDetailDto;
import ru.skillbox.skillfitbox.dto.ClientDto;
import ru.skillbox.skillfitbox.entity.AdditionalService;
import ru.skillbox.skillfitbox.entity.Client;
import ru.skillbox.skillfitbox.entity.Locker;
import ru.skillbox.skillfitbox.entity.Trainer;
import ru.skillbox.skillfitbox.mapper.ClientMapper;
import ru.skillbox.skillfitbox.mapper.LockerMapper;
import ru.skillbox.skillfitbox.mapper.TrainerMapper;
import ru.skillbox.skillfitbox.repository.ClientRepository;
import ru.skillbox.skillfitbox.repository.LockerRepository;
import ru.skillbox.skillfitbox.repository.AdditionalServiceRepository;
import ru.skillbox.skillfitbox.repository.TrainerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервисный класс для бизнес-логики операций с клиентами.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final TrainerRepository trainerRepository;
    private final LockerRepository lockerRepository;
    private final AdditionalServiceRepository additionalServiceRepository;
    private final ClientMapper clientMapper;
    private final TrainerMapper trainerMapper;
    private final LockerMapper lockerMapper;

    /**
     * Добавляет нового клиента в систему.
     * 
     * @param clientDto данные клиента для добавления
     * @return созданный DTO клиента
     */
    public ClientDto addClient(ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        client.setIsActive(true);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDto(savedClient);
    }

    /**
     * Обновляет информацию о клиенте.
     * 
     * @param id ID клиента
     * @param clientDto обновленные данные клиента
     * @return обновленный DTO клиента
     */
    public ClientDto updateClient(UUID id, ClientDto clientDto) {
        Client existingClient = clientRepository.findClientDetailById(id);
        if (existingClient == null) {
            throw new RuntimeException("Клиент с ID " + id + " не найден");
        }
        
        clientDto.setId(id);
        Client client = clientMapper.toEntity(clientDto);
        client.setServices(existingClient.getServices());
        client.setLocker(existingClient.getLocker());
        client.setTrainer(existingClient.getTrainer());
        client.setCreatedDatetime(existingClient.getCreatedDatetime());
        Client updatedClient = clientRepository.update(client);
        return clientMapper.toDto(updatedClient);
    }

    /**
     * Получает всех клиентов.
     * 
     * @return список всех DTO клиентов
     */
    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Получает краткую информацию о клиенте по ID.
     * 
     * @param id ID клиента
     * @return DTO клиента или null если не найден
     */
    public ClientDto getClientById(UUID id) {
        Client client = clientRepository.findById(id);
        return client != null ? clientMapper.toDto(client) : null;
    }

    /**
     * Получает подробную информацию о клиенте по ID включая тренера, услуги и шкафчик.
     * Использует один SQL-запрос с JOIN для оптимизации производительности.
     * 
     * @param id ID клиента
     * @return подробный DTO клиента или null если не найден
     */
    public ClientDetailDto getClientDetailById(UUID id) {
        Client client = clientRepository.findClientDetailById(id);
        if (client == null) {
            return null;
        }

        ClientDetailDto detailDto = new ClientDetailDto();
        detailDto.setId(client.getId());
        detailDto.setSurname(client.getSurname());
        detailDto.setName(client.getName());
        detailDto.setPatronymic(client.getPatronymic());
        detailDto.setBirthday(client.getBirthday());
        detailDto.setPhone(client.getPhone());
        detailDto.setEmail(client.getEmail());
        detailDto.setIsActive(client.getIsActive());

        // Заполняем информацию о тренере
        if (client.getTrainer() != null) {
            detailDto.setTrainer(trainerMapper.toDto(client.getTrainer()));
        }

        // Заполняем информацию о шкафчике
        if (client.getLocker() != null) {
            detailDto.setLocker(lockerMapper.toDto(client.getLocker()));
        }

        // Заполняем услуги
        if (client.getServices() != null && !client.getServices().isEmpty()) {
            List<String> serviceDtos = client.getServices().stream()
                    .map(AdditionalService::getName)
                    .collect(Collectors.toList());
            detailDto.setServices(serviceDtos);
        } else {
            detailDto.setServices(new ArrayList<>());
        }

        return detailDto;
    }

    /**
     * Активирует или деактивирует клиента.
     * 
     * @param id ID клиента
     * @param isActive новый статус активности
     */
    public void updateClientStatus(UUID id, Boolean isActive) {
        Client client = clientRepository.findClientDetailById(id);
        if (client == null) {
            throw new RuntimeException("Клиент с ID " + id + " не найден");
        }
        client.setIsActive(isActive);
        clientRepository.update(client);
    }

    /**
     * Назначает тренера клиенту.
     * 
     * @param clientId ID клиента
     * @param trainerId ID тренера
     */
    public void assignTrainer(UUID clientId, UUID trainerId) {
        Client client = clientRepository.findClientDetailById(clientId);
        if (client == null) {
            throw new RuntimeException("Клиент с ID " + clientId + " не найден");
        }
        
        Trainer trainer = trainerRepository.findById(trainerId);
        if (trainer == null) {
            throw new RuntimeException("Тренер с ID " + trainerId + " не найден");
        }
        
        client.setTrainer(trainer);

        clientRepository.update(client);
    }

    /**
     * Добавляет дополнительную услугу клиенту.
     * 
     * @param clientId ID клиента
     * @param serviceId ID услуги
     */
    public void addServiceToClient(UUID clientId, String serviceId) {
        Client client = clientRepository.findById(clientId);
        if (client == null) {
            throw new RuntimeException("Клиент с ID " + clientId + " не найден");
        }
        
        AdditionalService service = additionalServiceRepository.findById(serviceId);
        if (service == null) {
            throw new RuntimeException("Услуга с ID " + serviceId + " не найдена");
        }
        
        additionalServiceRepository.addServiceToClient(clientId, serviceId);
    }

    /**
     * Назначает шкафчик клиенту.
     * 
     * @param clientId ID клиента
     * @param lockerId ID шкафчика
     */
    public void assignLocker(UUID clientId, UUID lockerId) {
        Client client = clientRepository.findClientDetailById(clientId);
        if (client == null) {
            throw new RuntimeException("Клиент с ID " + clientId + " не найден");
        }
        
        Locker locker = lockerRepository.findById(lockerId);
        if (locker == null) {
            throw new RuntimeException("Шкафчик с ID " + lockerId + " не найден");
        }
        
        if (locker.getClient() != null && locker.getClient().getId() != null) {
            if (Objects.equals(clientId, locker.getClient().getId())) {
                log.info("Шкафчик уже занят данным клиентом - ничего не делаем");
                return;
            }
            throw new RuntimeException("Шкафчик уже занят");
        }
        
        // Удаляем предыдущее назначение шкафчика если существует
        if (client.getLocker() != null) {
            client.getLocker().setClient(null);
            lockerRepository.update(client.getLocker());
        }
        
        client.setLocker(locker);
        locker.setClient(client);

        clientRepository.update(client);
        lockerRepository.update(locker);
    }
}
