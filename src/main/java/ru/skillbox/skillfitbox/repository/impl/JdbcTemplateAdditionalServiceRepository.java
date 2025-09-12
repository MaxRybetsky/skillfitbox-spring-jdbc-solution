package ru.skillbox.skillfitbox.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.skillbox.skillfitbox.entity.AdditionalService;
import ru.skillbox.skillfitbox.repository.AdditionalServiceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@Primary
@Slf4j
@RequiredArgsConstructor
public class JdbcTemplateAdditionalServiceRepository implements AdditionalServiceRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<AdditionalService> serviceRowMapper = (rs, rowNum) -> {
        AdditionalService service = new AdditionalService();
        service.setId(rs.getString("id"));
        service.setName(rs.getString("name"));
        service.setCreatedDatetime(rs.getObject("created_datetime", LocalDateTime.class));
        service.setUpdatedDatetime(rs.getObject("updated_datetime", LocalDateTime.class));
        return service;
    };

    @Override
    public AdditionalService findById(String id) {
        log.info("Entering findById({})", id);
        
        String sql = "SELECT * FROM services WHERE id = ?";
        
        try {
            AdditionalService service = jdbcTemplate.queryForObject(sql, serviceRowMapper, id);
            log.info("Successfully found service with id: {}", id);
            return service;
        } catch (Exception e) {
            log.warn("Service not found with id: {}", id);
            return null;
        }
    }

    @Override
    public List<AdditionalService> findAll() {
        log.info("Entering findAll()");
        
        String sql = "SELECT * FROM services ORDER BY name";
        
        List<AdditionalService> services = jdbcTemplate.query(sql, serviceRowMapper);
        log.info("Successfully retrieved {} services", services.size());
        
        return services;
    }

    @Override
    public void addServiceToClient(UUID clientId, String serviceId) {
        log.info("Entering addServiceToClient({}, {})", clientId, serviceId);
        
        String sql = "INSERT INTO client_services (client_id, service_id) VALUES (?, ?) ON CONFLICT (client_id, service_id) DO NOTHING";
        
        int rowsAffected = jdbcTemplate.update(sql, clientId, serviceId);
        
        if (rowsAffected > 0) {
            log.info("Successfully added service {} to client {}", serviceId, clientId);
        } else {
            log.info("Service {} already exists for client {} (conflict ignored)", serviceId, clientId);
        }
    }

    @Override
    public List<String> findClientNamesByServiceId(String serviceId) {
        log.info("Entering findClientNamesByServiceId({})", serviceId);
        
        String sql = "SELECT CONCAT(c.surname, ' ', c.name, ' ', COALESCE(c.patronymic, '')) as client_name " +
                "FROM clients c " +
                "INNER JOIN client_services cs ON c.id = cs.client_id " +
                "WHERE cs.service_id = ? ORDER BY client_name";
        
        List<String> names = jdbcTemplate.queryForList(sql, String.class, serviceId);
        log.info("Successfully retrieved {} client names for service: {}", names.size(), serviceId);
        
        return names;
    }
}
