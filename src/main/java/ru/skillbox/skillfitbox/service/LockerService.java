package ru.skillbox.skillfitbox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.skillfitbox.dto.LockerDto;
import ru.skillbox.skillfitbox.mapper.LockerMapper;
import ru.skillbox.skillfitbox.repository.LockerRepository;

import java.util.List;

/**
 * Сервисный класс для бизнес-логики операций со шкафчиками.
 */
@Service
@RequiredArgsConstructor
public class LockerService {

    private final LockerRepository lockerRepository;
    private final LockerMapper lockerMapper;

    /**
     * Получает информацию о всех шкафчиках включая полные имена клиентов.
     * 
     * @return список DTO шкафчиков с информацией о клиентах
     */
    public List<LockerDto> getAllLockersWithClientInfo() {
        return lockerRepository.findAllWithClientInfo().stream()
                .map(lockerMapper::toDto)
                .toList();
    }
}
