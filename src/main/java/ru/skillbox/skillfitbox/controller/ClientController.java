package ru.skillbox.skillfitbox.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.skillfitbox.dto.ClientDetailDto;
import ru.skillbox.skillfitbox.dto.ClientDto;
import ru.skillbox.skillfitbox.service.ClientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Управление клиентами", description = "API для управления клиентами фитнес-центра")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Создать нового клиента", description = "Добавить нового клиента в систему фитнес-центра")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Клиент успешно создан",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "surname": "Иванов",
                                        "name": "Иван",
                                        "patronymic": "Иванович",
                                        "birthday": "1990-05-15",
                                        "phone": "+79123456789",
                                        "email": "ivan.ivanov@example.com",
                                        "isActive": true
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные",
                    content = @Content)
    })
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ClientDto> addClient(
            @Parameter(description = "Информация о клиенте", required = true)
            @Valid @RequestBody ClientDto clientDto) {
        ClientDto createdClient = clientService.addClient(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);

    }

    @Operation(summary = "Обновить информацию о клиенте", description = "Обновить существующую информацию о клиенте по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о клиенте успешно обновлена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "123e4567-e89b-12d3-a456-426614174000",
                                        "surname": "Иванов",
                                        "name": "Иван",
                                        "patronymic": "Иванович",
                                        "birthday": "1990-05-15",
                                        "phone": "+79123456789",
                                        "email": "ivan.ivanov.updated@example.com",
                                        "isActive": true
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "Клиент не найден",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(
            @Parameter(description = "ID клиента", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Обновленная информация о клиенте", required = true)
            @Valid @RequestBody ClientDto clientDto) {
        ClientDto updatedClient = clientService.updateClient(id, clientDto);
        return ResponseEntity.ok(updatedClient);
    }

    @Operation(summary = "Получить всех клиентов", description = "Получить список всех клиентов в системе")
    @ApiResponse(responseCode = "200", description = "Список клиентов успешно получен",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ClientDto.class)))
    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<ClientDto> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @Operation(summary = "Получить клиента по ID", description = "Получить конкретного клиента по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клиент найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "123e4567-e89b-12d3-a456-426614174000",
                                        "surname": "Иванов",
                                        "name": "Иван",
                                        "patronymic": "Иванович",
                                        "birthday": "1990-05-15",
                                        "phone": "+79123456789",
                                        "email": "ivan.ivanov@example.com",
                                        "isActive": true
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "Клиент не найден",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(
            @Parameter(description = "ID клиента", required = true)
            @PathVariable UUID id) {
        ClientDto client = clientService.getClientById(id);
        return client != null ? ResponseEntity.ok(client) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Получить детальную информацию о клиенте", description = "Получить детальную информацию о клиенте, включая связанные данные")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Детальная информация о клиенте найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDetailDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "123e4567-e89b-12d3-a456-426614174000",
                                        "surname": "Иванов",
                                        "name": "Иван",
                                        "patronymic": "Иванович",
                                        "birthday": "1990-05-15",
                                        "phone": "+79123456789",
                                        "email": "ivan.ivanov@example.com",
                                        "isActive": true,
                                        "locker": {
                                            "id": "123e4567-e89b-12d3-a456-426614174002",
                                            "number": 1,
                                            "clientFullName": "Иванов Иван Иванович"
                                        },
                                        "trainer": {
                                            "id": "123e4567-e89b-12d3-a456-426614174001",
                                            "surname": "Петров",
                                            "name": "Петр",
                                            "patronymic": "Петрович",
                                            "phone": "+79123456790",
                                            "status": "WORKING"
                                        },
                                        "services": ["SOLARIUM", "POOL"]
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "Клиент не найден",
                    content = @Content)
    })
    @GetMapping("/{id}/detail")
    public ResponseEntity<ClientDetailDto> getClientDetailById(
            @Parameter(description = "ID клиента", required = true)
            @PathVariable UUID id) {
        ClientDetailDto clientDetail = clientService.getClientDetailById(id);
        return clientDetail != null ? ResponseEntity.ok(clientDetail) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Обновить статус клиента", description = "Активировать или деактивировать клиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус клиента успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateClientStatus(
            @Parameter(description = "ID клиента", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Новый статус (true для активного, false для неактивного)", required = true)
            @RequestParam Boolean isActive) {
        clientService.updateClientStatus(id, isActive);
        return ResponseEntity.ok().build();

    }

    @Operation(summary = "Назначить тренера клиенту", description = "Назначить тренера конкретному клиенту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тренер успешно назначен"),
            @ApiResponse(responseCode = "404", description = "Клиент или тренер не найден")
    })
    @PostMapping("/{clientId}/trainer/{trainerId}")
    public ResponseEntity<Void> assignTrainer(
            @Parameter(description = "ID клиента", required = true)
            @PathVariable UUID clientId,
            @Parameter(description = "ID тренера", required = true)
            @PathVariable UUID trainerId) {
        clientService.assignTrainer(clientId, trainerId);
        return ResponseEntity.ok().build();

    }

    @Operation(summary = "Добавить услугу клиенту", description = "Добавить дополнительную услугу клиенту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Услуга успешно добавлена"),
            @ApiResponse(responseCode = "404", description = "Клиент или услуга не найдены")
    })
    @PostMapping("/{clientId}/services/{serviceId}")
    public ResponseEntity<Void> addServiceToClient(
            @Parameter(description = "ID клиента", required = true)
            @PathVariable UUID clientId,
            @Parameter(description = "ID услуги", required = true)
            @PathVariable String serviceId) {
        clientService.addServiceToClient(clientId, serviceId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Назначить шкафчик клиенту", description = "Назначить шкафчик конкретному клиенту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Шкафчик успешно назначен"),
            @ApiResponse(responseCode = "404", description = "Клиент или шкафчик не найдены")
    })
    @PostMapping("/{clientId}/locker/{lockerId}")
    public ResponseEntity<Void> assignLocker(
            @Parameter(description = "ID клиента", required = true)
            @PathVariable UUID clientId,
            @Parameter(description = "ID шкафчика", required = true)
            @PathVariable UUID lockerId) {
        clientService.assignLocker(clientId, lockerId);
        return ResponseEntity.ok().build();
    }
}
