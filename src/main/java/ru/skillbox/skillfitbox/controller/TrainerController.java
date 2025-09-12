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
import ru.skillbox.skillfitbox.dto.TrainerDetailDto;
import ru.skillbox.skillfitbox.dto.TrainerDto;
import ru.skillbox.skillfitbox.entity.TrainerStatus;
import ru.skillbox.skillfitbox.service.TrainerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trainers")
@Tag(name = "Управление тренерами", description = "API для управления тренерами фитнес-центра")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    @Operation(summary = "Создать нового тренера", description = "Добавить нового тренера в систему фитнес-центра")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Тренер успешно создан",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrainerDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "surname": "Петров",
                                        "name": "Петр",
                                        "patronymic": "Петрович",
                                        "phone": "+79123456790",
                                        "status": "WORKING"
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<TrainerDto> addTrainer(
            @Parameter(description = "Информация о тренере", required = true)
            @Valid @RequestBody TrainerDto trainerDto) {
        try {
            TrainerDto createdTrainer = trainerService.addTrainer(trainerDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrainer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Обновить информацию о тренере", description = "Обновить существующую информацию о тренере по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о тренере успешно обновлена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrainerDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "123e4567-e89b-12d3-a456-426614174001",
                                        "surname": "Петров",
                                        "name": "Петр",
                                        "patronymic": "Петрович",
                                        "phone": "+79123456790",
                                        "status": "WORKING"
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "Тренер не найден",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<TrainerDto> updateTrainer(
            @Parameter(description = "ID тренера", required = true)
            @PathVariable UUID id, 
            @Parameter(description = "Обновленная информация о тренере", required = true)
            @Valid @RequestBody TrainerDto trainerDto) {
        try {
            TrainerDto updatedTrainer = trainerService.updateTrainer(id, trainerDto);
            return ResponseEntity.ok(updatedTrainer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Изменить статус тренера", description = "Изменить статус тренера (WORKING, ON_LEAVE, NOT_WORKING)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус тренера успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Тренер не найден")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeTrainerStatus(
            @Parameter(description = "ID тренера", required = true)
            @PathVariable UUID id, 
            @Parameter(description = "Новый статус тренера", required = true)
            @RequestParam TrainerStatus status) {
        try {
            trainerService.changeTrainerStatus(id, status);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Получить детальную информацию о тренере", description = "Получить детальную информацию о тренере, включая связанные данные")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Детальная информация о тренере найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrainerDetailDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "123e4567-e89b-12d3-a456-426614174001",
                                        "surname": "Петров",
                                        "name": "Петр",
                                        "patronymic": "Петрович",
                                        "phone": "+79123456790",
                                        "status": "WORKING",
                                        "clientNames": ["Иванов Иван Иванович", "Сидоров Алексей Владимирович"]
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "Тренер не найден",
                    content = @Content)
    })
    @GetMapping("/{id}/detail")
    public ResponseEntity<TrainerDetailDto> getTrainerDetailById(
            @Parameter(description = "ID тренера", required = true)
            @PathVariable UUID id) {
        TrainerDetailDto trainerDetail = trainerService.getTrainerDetailById(id);
        return trainerDetail != null ? ResponseEntity.ok(trainerDetail) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Получить всех тренеров", description = "Получить список всех тренеров в системе")
    @ApiResponse(responseCode = "200", description = "Список тренеров успешно получен",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TrainerDto.class)))
    @GetMapping
    public ResponseEntity<List<TrainerDto>> getAllTrainers() {
        List<TrainerDto> trainers = trainerService.getAllTrainers();
        return ResponseEntity.ok(trainers);
    }
}
