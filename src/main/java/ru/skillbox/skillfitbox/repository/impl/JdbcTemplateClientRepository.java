package ru.skillbox.skillfitbox.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.skillbox.skillfitbox.entity.AdditionalService;
import ru.skillbox.skillfitbox.entity.Client;
import ru.skillbox.skillfitbox.entity.Trainer;
import ru.skillbox.skillfitbox.entity.TrainerStatus;
import ru.skillbox.skillfitbox.entity.Locker;
import ru.skillbox.skillfitbox.repository.ClientRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@Primary
@Slf4j
@RequiredArgsConstructor
public class JdbcTemplateClientRepository implements ClientRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Client> clientRowMapper = (rs, rowNum) -> {
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
    };

    @Override
    public Client save(Client client) {
        log.info("Entering save(Client)");
        
        String sql = "INSERT INTO clients (id, surname, name, patronymic, birthday, phone, email, " +
                "is_active, locker_id, created_datetime, updated_datetime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        
        UUID lockerId = client.getLocker() != null ? client.getLocker().getId() : null;
        
        int rowsAffected = jdbcTemplate.update(sql, 
            id, 
            client.getSurname(), 
            client.getName(), 
            client.getPatronymic(), 
            client.getBirthday(), 
            client.getPhone(), 
            client.getEmail(), 
            client.getIsActive(), 
            lockerId, 
            now, 
            now
        );
        
        if (rowsAffected > 0) {
            client.setId(id);
            client.setCreatedDatetime(now);
            client.setUpdatedDatetime(now);
            log.info("Successfully saved client with id: {}", id);
        } else {
            log.error("Failed to save client");
            throw new RuntimeException("Failed to save client");
        }
        
        return client;
    }

    @Override
    public Client update(Client client) {
        log.info("Entering update(Client) with id: {}", client.getId());
        
        String sql = "UPDATE clients SET surname = ?, name = ?, patronymic = ?, birthday = ?, phone = ?, email = ?, " +
                "is_active = ?, locker_id = ?, trainer_id = ?, updated_datetime = ? WHERE id = ?";
        
        LocalDateTime now = LocalDateTime.now();
        
        UUID lockerId = client.getLocker() != null ? client.getLocker().getId() : null;
        UUID trainerId = client.getTrainer() != null ? client.getTrainer().getId() : null;
        
        int rowsAffected = jdbcTemplate.update(sql,
            client.getSurname(),
            client.getName(),
            client.getPatronymic(),
            client.getBirthday(),
            client.getPhone(),
            client.getEmail(),
            client.getIsActive(),
            lockerId,
            trainerId,
            now,
            client.getId()
        );
        
        if (rowsAffected > 0) {
            client.setUpdatedDatetime(now);
            log.info("Successfully updated client with id: {}", client.getId());
        } else {
            log.error("Failed to update client with id: {}", client.getId());
            throw new RuntimeException("Failed to update client");
        }
        
        return client;
    }

    @Override
    public Client findById(UUID id) {
        log.info("Entering findById({})", id);
        
        String sql = "SELECT * FROM clients WHERE id = ?";
        
        try {
            Client client = jdbcTemplate.queryForObject(sql, clientRowMapper, id);
            log.info("Successfully found client with id: {}", id);
            return client;
        } catch (Exception e) {
            log.warn("Client not found with id: {}", id);
            return null;
        }
    }

    @Override
    public List<Client> findAll() {
        log.info("Entering findAll()");
        
        String sql = "SELECT * FROM clients ORDER BY created_datetime DESC";
        
        List<Client> clients = jdbcTemplate.query(sql, clientRowMapper);
        log.info("Successfully retrieved {} clients", clients.size());
        
        return clients;
    }

    @Override
    public List<String> findClientNamesByTrainerId(UUID trainerId) {
        log.info("Entering findClientNamesByTrainerId({})", trainerId);
        
        String sql = "SELECT CONCAT(surname, ' ', name, ' ', COALESCE(patronymic, '')) as client_name FROM clients WHERE trainer_id = ? ORDER BY surname, name";
        
        List<String> names = jdbcTemplate.queryForList(sql, String.class, trainerId);
        log.info("Successfully retrieved {} client names for trainer: {}", names.size(), trainerId);
        
        return names;
    }

    @Override
    public Client findClientDetailById(UUID id) {
        log.info("Entering findClientDetailById({})", id);
        
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

        List<Client> clients = jdbcTemplate.query(sql, (rs, rowNum) -> {
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
            
            // Set trainer if exists
            if (rs.getString("trainer_id") != null) {
                Trainer trainer = new Trainer();
                trainer.setId(UUID.fromString(rs.getString("trainer_id")));
                trainer.setSurname(rs.getString("trainer_surname"));
                trainer.setName(rs.getString("trainer_name"));
                trainer.setPatronymic(rs.getString("trainer_patronymic"));
                trainer.setPhone(rs.getString("trainer_phone"));
                trainer.setStatus(TrainerStatus.valueOf(rs.getString("trainer_status")));
                trainer.setCreatedDatetime(rs.getObject("trainer_created_datetime", LocalDateTime.class));
                trainer.setUpdatedDatetime(rs.getObject("trainer_updated_datetime", LocalDateTime.class));
                client.setTrainer(trainer);
            }
            
            // Set locker if exists
            if (rs.getString("locker_id") != null) {
                Locker locker = new Locker();
                locker.setId(UUID.fromString(rs.getString("locker_id")));
                locker.setNumber(rs.getInt("locker_number"));
                locker.setCreatedDatetime(rs.getObject("locker_created_datetime", LocalDateTime.class));
                locker.setUpdatedDatetime(rs.getObject("locker_updated_datetime", LocalDateTime.class));
                client.setLocker(locker);
            }
            
            return client;
        }, id);

        if (clients.isEmpty()) {
            log.warn("Client not found with id: {}", id);
            return null;
        }

        // Get services separately to avoid duplicate client records
        String servicesSql = """
                SELECT s.id, s.name, s.created_datetime, s.updated_datetime
                FROM services s
                INNER JOIN client_services cs ON s.id = cs.service_id
                WHERE cs.client_id = ?
                ORDER BY s.name
                """;
        
        List<AdditionalService> services = jdbcTemplate.query(servicesSql, (rs, rowNum) -> {
            AdditionalService service = new AdditionalService();
            service.setId(rs.getString("id"));
            service.setName(rs.getString("name"));
            service.setCreatedDatetime(rs.getObject("created_datetime", LocalDateTime.class));
            service.setUpdatedDatetime(rs.getObject("updated_datetime", LocalDateTime.class));
            return service;
        }, id);

        Client client = clients.get(0);
        client.setServices(services);
        
        log.info("Successfully retrieved client detail with id: {} and {} services", id, services.size());
        return client;
    }
}
