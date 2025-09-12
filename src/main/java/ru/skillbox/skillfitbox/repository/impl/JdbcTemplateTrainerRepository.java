package ru.skillbox.skillfitbox.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.skillbox.skillfitbox.entity.Trainer;
import ru.skillbox.skillfitbox.entity.TrainerStatus;
import ru.skillbox.skillfitbox.repository.TrainerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@Primary
@Slf4j
@RequiredArgsConstructor
public class JdbcTemplateTrainerRepository implements TrainerRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Trainer> trainerRowMapper = (rs, rowNum) -> {
        Trainer trainer = new Trainer();
        trainer.setId(UUID.fromString(rs.getString("id")));
        trainer.setSurname(rs.getString("surname"));
        trainer.setName(rs.getString("name"));
        trainer.setPatronymic(rs.getString("patronymic"));
        trainer.setPhone(rs.getString("phone"));
        trainer.setStatus(TrainerStatus.valueOf(rs.getString("status")));
        trainer.setCreatedDatetime(rs.getObject("created_datetime", LocalDateTime.class));
        trainer.setUpdatedDatetime(rs.getObject("updated_datetime", LocalDateTime.class));
        return trainer;
    };

    @Override
    public Trainer save(Trainer trainer) {
        log.info("Entering save(Trainer)");
        
        String sql = "INSERT INTO trainers (id, surname, name, patronymic, phone, status, created_datetime, updated_datetime) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        UUID id = trainer.getId() != null ? trainer.getId() : UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        
        int rowsAffected = jdbcTemplate.update(sql,
            id,
            trainer.getSurname(),
            trainer.getName(),
            trainer.getPatronymic(),
            trainer.getPhone(),
            trainer.getStatus().name(),
            now,
            now
        );
        
        if (rowsAffected > 0) {
            trainer.setId(id);
            trainer.setCreatedDatetime(now);
            trainer.setUpdatedDatetime(now);
            log.info("Successfully saved trainer with id: {}", id);
        } else {
            log.error("Failed to save trainer");
            throw new RuntimeException("Failed to save trainer");
        }
        
        return trainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
        log.info("Entering update(Trainer) with id: {}", trainer.getId());
        
        String sql = "UPDATE trainers SET surname = ?, name = ?, patronymic = ?, phone = ?, status = ?, updated_datetime = ? WHERE id = ?";
        
        LocalDateTime now = LocalDateTime.now();
        
        int rowsAffected = jdbcTemplate.update(sql,
            trainer.getSurname(),
            trainer.getName(),
            trainer.getPatronymic(),
            trainer.getPhone(),
            trainer.getStatus().name(),
            now,
            trainer.getId()
        );
        
        if (rowsAffected > 0) {
            trainer.setUpdatedDatetime(now);
            log.info("Successfully updated trainer with id: {}", trainer.getId());
        } else {
            log.error("Failed to update trainer with id: {}", trainer.getId());
            throw new RuntimeException("Failed to update trainer");
        }
        
        return trainer;
    }

    @Override
    public Trainer findById(UUID id) {
        log.info("Entering findById({})", id);
        
        String sql = "SELECT * FROM trainers WHERE id = ?";
        
        try {
            Trainer trainer = jdbcTemplate.queryForObject(sql, trainerRowMapper, id);
            log.info("Successfully found trainer with id: {}", id);
            return trainer;
        } catch (Exception e) {
            log.warn("Trainer not found with id: {}", id);
            return null;
        }
    }

    @Override
    public List<Trainer> findAll() {
        log.info("Entering findAll()");
        
        String sql = "SELECT * FROM trainers ORDER BY created_datetime DESC";
        
        List<Trainer> trainers = jdbcTemplate.query(sql, trainerRowMapper);
        log.info("Successfully retrieved {} trainers", trainers.size());
        
        return trainers;
    }
}
