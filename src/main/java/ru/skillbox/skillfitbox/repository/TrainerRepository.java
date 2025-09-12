package ru.skillbox.skillfitbox.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.skillbox.skillfitbox.entity.Trainer;
import ru.skillbox.skillfitbox.entity.TrainerStatus;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TrainerRepository {

    private final DataSource dataSource;

    private Trainer mapTrainer(ResultSet rs) throws SQLException {
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
    }

    public Trainer save(Trainer trainer) {
        String sql = "INSERT INTO trainers (id, surname, name, patronymic, phone, status, created_datetime, updated_datetime) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        UUID id = trainer.getId() != null ? trainer.getId() : UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.setString(2, trainer.getSurname());
            ps.setString(3, trainer.getName());
            ps.setString(4, trainer.getPatronymic());
            ps.setString(5, trainer.getPhone());
            ps.setString(6, trainer.getStatus().name());
            ps.setObject(7, now);
            ps.setObject(8, now);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения тренера", e);
        }
        trainer.setId(id);
        trainer.setCreatedDatetime(now);
        trainer.setUpdatedDatetime(now);
        return trainer;
    }

    public Trainer update(Trainer trainer) {
        String sql = "UPDATE trainers SET surname = ?, name = ?, patronymic = ?, phone = ?, status = ?, updated_datetime = ? WHERE id = ?";
        LocalDateTime now = LocalDateTime.now();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trainer.getSurname());
            ps.setString(2, trainer.getName());
            ps.setString(3, trainer.getPatronymic());
            ps.setString(4, trainer.getPhone());
            ps.setString(5, trainer.getStatus().name());
            ps.setObject(6, now);
            ps.setObject(7, trainer.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления тренера", e);
        }
        trainer.setUpdatedDatetime(now);
        return trainer;
    }

    public Trainer findById(UUID id) {
        String sql = "SELECT * FROM trainers WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapTrainer(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска тренера по id", e);
        }
        return null;
    }

    public List<Trainer> findAll() {
        String sql = "SELECT * FROM trainers ORDER BY created_datetime DESC";
        List<Trainer> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapTrainer(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения списка тренеров", e);
        }
        return result;
    }
}
