package ru.skillbox.skillfitbox.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.skillbox.skillfitbox.entity.AdditionalService;
import ru.skillbox.skillfitbox.entity.Client;
import ru.skillbox.skillfitbox.entity.Trainer;
import ru.skillbox.skillfitbox.entity.TrainerStatus;
import ru.skillbox.skillfitbox.entity.Locker;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ClientRepository {

    private final DataSource dataSource;

    private Client mapClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(UUID.fromString(rs.getString("id")));
        client.setSurname(rs.getString("surname"));
        client.setName(rs.getString("name"));
        client.setPatronymic(rs.getString("patronymic"));
        client.setBirthday(rs.getObject("birthday", LocalDate.class));
        client.setPhone(rs.getString("phone"));
        client.setEmail(rs.getString("email"));
        client.setIsActive(rs.getBoolean("is_active"));
        client.setCreatedDatetime(rs.getObject("created_datetime", LocalDateTime.class));
        client.setUpdatedDatetime(rs.getObject("updated_datetime", LocalDateTime.class));
        return client;
    }

    public Client save(Client client) {
        String sql = "INSERT INTO clients (id, surname, name, patronymic, birthday, phone, email, " +
                "is_active, locker_id, created_datetime, updated_datetime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            UUID lockerId = Optional.ofNullable(client.getLocker())
                    .map(Locker::getId)
                    .orElse(null);

            ps.setObject(1, id);
            ps.setString(2, client.getSurname());
            ps.setString(3, client.getName());
            ps.setString(4, client.getPatronymic());
            ps.setObject(5, client.getBirthday());
            ps.setString(6, client.getPhone());
            ps.setString(7, client.getEmail());
            ps.setObject(8, client.getIsActive());
            ps.setObject(9, lockerId);
            ps.setObject(10, now);
            ps.setObject(11, now);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка сохранения клиента", e);
            throw new RuntimeException("Ошибка сохранения клиента", e);
        }
        client.setId(id);
        client.setCreatedDatetime(now);
        client.setUpdatedDatetime(now);
        return client;
    }

    public Client update(Client client) {
        String sql = "UPDATE clients SET surname = ?, name = ?, patronymic = ?, birthday = ?, phone = ?, email = ?, " +
                "is_active = ?, locker_id = ?, trainer_id = ?, updated_datetime = ? WHERE id = ?";
        LocalDateTime now = LocalDateTime.now();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, client.getSurname());
            ps.setString(2, client.getName());
            ps.setString(3, client.getPatronymic());
            ps.setObject(4, client.getBirthday());
            ps.setString(5, client.getPhone());
            ps.setString(6, client.getEmail());
            ps.setObject(7, client.getIsActive());
            ps.setObject(8, client.getLocker() != null ? client.getLocker().getId() : null);
            ps.setObject(9, client.getTrainer() != null ? client.getTrainer().getId() : null);
            ps.setObject(10, now);
            ps.setObject(11, client.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка обновления клиента", e);
            throw new RuntimeException("Ошибка обновления клиента", e);
        }
        client.setUpdatedDatetime(now);
        return client;
    }

    public Client findById(UUID id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapClient(rs);
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка поиска клиента по id", e);
            throw new RuntimeException("Ошибка поиска клиента по id", e);
        }
        return null;
    }

    public List<Client> findAll() {
        String sql = "SELECT * FROM clients ORDER BY created_datetime DESC";
        List<Client> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapClient(rs));
            }
        } catch (SQLException e) {
            log.error("Ошибка получения списка клиентов", e);
            throw new RuntimeException("Ошибка получения списка клиентов", e);
        }
        return result;
    }

    public List<String> findClientNamesByTrainerId(UUID trainerId) {
        String sql = "SELECT CONCAT(surname, ' ', name, ' ', COALESCE(patronymic, '')) as client_name FROM clients WHERE trainer_id = ? ORDER BY surname, name";
        List<String> names = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, trainerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    names.add(rs.getString("client_name"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения имен клиентов тренера", e);
        }
        return names;
    }

    public Client findClientDetailById(UUID id) {
        String sql = """
                SELECT
                    c.*,
                
                    t.id as trainer_id,
                    t.surname as trainer_surname,
                    t.name as trainer_name,
                    t.patronymic as trainer_patronymic,
                    t.phone as trainer_phone,
                    t.status as trainer_status,
                    t.created_datetime as trainer_created_datetime,
                    t.updated_datetime as trainer_updated_datetime,
                
                    l.id as locker_id,
                    l.number as locker_number,
                    l.created_datetime as locker_created_datetime,
                    l.updated_datetime as locker_updated_datetime,
                
                    s.id as service_id,
                    s.name as service_name,
                    s.created_datetime as service_created_datetime,
                    s.updated_datetime as service_updated_datetime
                FROM clients c
                LEFT JOIN trainers t ON c.trainer_id = t.id
                LEFT JOIN lockers l ON c.locker_id = l.id
                LEFT JOIN client_services cs ON c.id = cs.client_id
                LEFT JOIN services s ON cs.service_id = s.id
                WHERE c.id = ?
                ORDER BY s.name
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Client client = null;
                List<AdditionalService> services = new ArrayList<>();

                while (rs.next()) {
                    if (client == null) {
                        // Создаем основную информацию о клиенте только один раз
                        client = mapClient(rs);

                        // Заполняем информацию о тренере
                        if (rs.getString("trainer_id") != null) {
                            client.setTrainer(mapTrainer(rs));
                        }

                        // Заполняем информацию о шкафчике
                        if (rs.getString("locker_id") != null) {
                            client.setLocker(mapLocker(rs));
                        }
                    }

                    // Собираем услуги
                    if (rs.getString("service_id") != null) {
                        AdditionalService serviceInfo = new AdditionalService();
                        serviceInfo.setId(rs.getString("service_id"));
                        serviceInfo.setName(rs.getString("service_name"));
                        serviceInfo.setCreatedDatetime(rs.getObject("service_created_datetime", LocalDateTime.class));
                        serviceInfo.setUpdatedDatetime(rs.getObject("service_updated_datetime", LocalDateTime.class));
                        services.add(serviceInfo);
                    }
                }

                if (client != null) {
                    client.setServices(services);
                }

                return client;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения детальной информации о клиенте", e);
        }
    }

    private Trainer mapTrainer(ResultSet rs) throws SQLException {
        Trainer trainer = new Trainer();
        trainer.setId(UUID.fromString(rs.getString("trainer_id")));
        trainer.setSurname(rs.getString("trainer_surname"));
        trainer.setName(rs.getString("trainer_name"));
        trainer.setPatronymic(rs.getString("trainer_patronymic"));
        trainer.setPhone(rs.getString("trainer_phone"));
        trainer.setStatus(TrainerStatus.valueOf(rs.getString("trainer_status")));
        trainer.setCreatedDatetime(rs.getObject("trainer_created_datetime", LocalDateTime.class));
        trainer.setUpdatedDatetime(rs.getObject("trainer_updated_datetime", LocalDateTime.class));
        return trainer;
    }

    private Locker mapLocker(ResultSet rs) throws SQLException {
        Locker locker = new Locker();
        locker.setId(UUID.fromString(rs.getString("locker_id")));
        locker.setNumber(rs.getInt("locker_number"));
        locker.setCreatedDatetime(rs.getObject("locker_created_datetime", LocalDateTime.class));
        locker.setUpdatedDatetime(rs.getObject("locker_updated_datetime", LocalDateTime.class));
        return locker;
    }
}
