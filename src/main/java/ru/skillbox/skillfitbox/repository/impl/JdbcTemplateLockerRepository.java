package ru.skillbox.skillfitbox.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.skillbox.skillfitbox.entity.Client;
import ru.skillbox.skillfitbox.entity.Locker;
import ru.skillbox.skillfitbox.repository.LockerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@Primary
@Slf4j
@RequiredArgsConstructor
public class JdbcTemplateLockerRepository implements LockerRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Locker> lockerWithClientRowMapper = (rs, rowNum) -> {
        Locker locker = new Locker();
        locker.setId(rs.getObject("l_id", UUID.class));
        locker.setNumber(rs.getInt("l_number"));
        locker.setCreatedDatetime(rs.getObject("l_created_datetime", LocalDateTime.class));
        locker.setUpdatedDatetime(rs.getObject("l_updated_datetime", LocalDateTime.class));
        
        // Map client if exists
        if (rs.getObject("id", UUID.class) != null) {
            Client client = new Client();
            client.setId(rs.getObject("id", UUID.class));
            client.setSurname(rs.getString("surname"));
            client.setName(rs.getString("name"));
            client.setPatronymic(rs.getString("patronymic"));
            client.setBirthday(rs.getObject("birthday", LocalDate.class));
            client.setPhone(rs.getString("phone"));
            client.setEmail(rs.getString("email"));
            client.setIsActive(rs.getBoolean("is_active"));
            client.setCreatedDatetime(rs.getObject("created_datetime", LocalDateTime.class));
            client.setUpdatedDatetime(rs.getObject("updated_datetime", LocalDateTime.class));
            locker.setClient(client);
        }
        
        return locker;
    };

    @Override
    public Locker findById(UUID id) {
        log.info("Entering findById({})", id);
        
        String sql = """
                SELECT c.*,
                    l.id as l_id,
                    l.number as l_number,
                    l.created_datetime as l_created_datetime,
                    l.updated_datetime as l_updated_datetime
                FROM lockers l LEFT JOIN clients c ON l.client_id = c.id WHERE l.id = ?
                """;
        
        try {
            Locker locker = jdbcTemplate.queryForObject(sql, lockerWithClientRowMapper, id);
            log.info("Successfully found locker with id: {}", id);
            return locker;
        } catch (Exception e) {
            log.warn("Locker not found with id: {}", id);
            return null;
        }
    }

    @Override
    public List<Locker> findAllWithClientInfo() {
        log.info("Entering findAllWithClientInfo()");
        
        String sql = """
                SELECT c.*,
                    l.id as l_id,
                    l.number as l_number,
                    l.created_datetime as l_created_datetime,
                    l.updated_datetime as l_updated_datetime
                FROM lockers l LEFT JOIN clients c ON l.client_id = c.id ORDER BY l.number
                """;
        
        List<Locker> lockers = jdbcTemplate.query(sql, lockerWithClientRowMapper);
        log.info("Successfully retrieved {} lockers", lockers.size());
        
        return lockers;
    }

    @Override
    public void update(Locker locker) {
        log.info("Entering update(Locker) with id: {}", locker.getId());
        
        String sql = "UPDATE lockers SET client_id = ?, updated_datetime = ? WHERE id = ?";
        
        UUID clientId = locker.getClient() != null ? locker.getClient().getId() : null;
        LocalDateTime now = LocalDateTime.now();
        
        int rowsAffected = jdbcTemplate.update(sql, clientId, now, locker.getId());
        
        if (rowsAffected > 0) {
            log.info("Successfully updated locker with id: {}", locker.getId());
        } else {
            log.error("Failed to update locker with id: {}", locker.getId());
            throw new RuntimeException("Failed to update locker");
        }
    }
}
