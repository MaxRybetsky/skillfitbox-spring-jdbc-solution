package ru.skillbox.skillfitbox.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.skillfitbox.dto.LockerDto;
import ru.skillbox.skillfitbox.service.LockerService;

import java.util.List;

@RestController
@RequestMapping("/api/lockers")
@Tag(name = "Управление шкафчиками", description = "API для управления шкафчиками фитнес-центра")
@RequiredArgsConstructor
public class LockerController {

    private final LockerService lockerService;

    @Operation(summary = "Получить все шкафчики с информацией о клиентах", description = "Получить список всех шкафчиков с информацией о назначенных клиентах")
    @ApiResponse(responseCode = "200", description = "Список шкафчиков успешно получен",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LockerDto.class),
                    examples = @ExampleObject(value = """
                            [
                                {
                                    "id": "123e4567-e89b-12d3-a456-426614174002",
                                    "number": 1,
                                    "clientFullName": "Иванов Иван Иванович"
                                },
                                {
                                    "id": "123e4567-e89b-12d3-a456-426614174003",
                                    "number": 2,
                                    "clientFullName": "Сидоров Алексей Владимирович"
                                },
                                {
                                    "id": "123e4567-e89b-12d3-a456-426614174004",
                                    "number": 3,
                                    "clientFullName": null
                                }
                            ]
                            """)))
    @GetMapping
    public ResponseEntity<List<LockerDto>> getAllLockersWithClientInfo() {
        List<LockerDto> lockers = lockerService.getAllLockersWithClientInfo();
        return ResponseEntity.ok(lockers);
    }
}
