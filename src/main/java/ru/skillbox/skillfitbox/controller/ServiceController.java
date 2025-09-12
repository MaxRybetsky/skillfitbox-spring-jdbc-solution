package ru.skillbox.skillfitbox.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.skillfitbox.dto.ServiceDto;
import ru.skillbox.skillfitbox.service.AdditionalServicesHandlingService;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@Tag(name = "Управление услугами", description = "API для управления дополнительными услугами")
@RequiredArgsConstructor
public class ServiceController {

    private final AdditionalServicesHandlingService additionalServicesHandlingService;

    @Operation(summary = "Получить все услуги", description = "Получить список всех доступных дополнительных услуг")
    @ApiResponse(responseCode = "200", description = "Список услуг успешно получен",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ServiceDto.class),
                    examples = @ExampleObject(value = """
                            [
                                {
                                    "id": "SOLARIUM",
                                    "name": "Солярий",
                                    "clientNames": ["Иванов Иван Иванович", "Сидоров Алексей Владимирович"]
                                },
                                {
                                    "id": "POOL",
                                    "name": "Бассейн",
                                    "clientNames": ["Иванов Иван Иванович"]
                                },
                                {
                                    "id": "SAUNA",
                                    "name": "Сауна",
                                    "clientNames": []
                                }
                            ]
                            """)))
    @GetMapping
    public ResponseEntity<List<ServiceDto>> getAllServices() {
        List<ServiceDto> services = additionalServicesHandlingService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @Operation(summary = "Получить услугу по ID с клиентами", description = "Получить конкретную услугу с информацией о клиентах, использующих её")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Услуга найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServiceDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "SOLARIUM",
                                        "name": "Солярий",
                                        "clientNames": ["Иванов Иван Иванович", "Сидоров Алексей Владимирович"]
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "Услуга не найдена",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDto> getServiceByIdWithClients(
            @Parameter(description = "ID услуги", required = true)
            @PathVariable String id) {
        ServiceDto service = additionalServicesHandlingService.getServiceByIdWithClients(id);
        return service != null ? ResponseEntity.ok(service) : ResponseEntity.notFound().build();
    }
}
