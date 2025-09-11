package ru.skillbox.skillfitbox.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.skillbox.skillfitbox.entity.AdditionalService;

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
public class AdditionalServiceRepository {

    private final DataSource dataSource;

    private AdditionalService mapService(ResultSet rs) throws SQLException {
        AdditionalService additionalService = new AdditionalService();
        additionalService.setId(rs.getString("id"));
        additionalService.setName(rs.getString("name"));
        additionalService.setCreatedDatetime(rs.getObject("created_datetime", LocalDateTime.class));
        additionalService.setUpdatedDatetime(rs.getObject("updated_datetime", LocalDateTime.class));
        return additionalService;
    }

    public AdditionalService findById(String id) {
        String sql = "SELECT * FROM services WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapService(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска услуги по id", e);
        }
        return null;
    }

    public List<AdditionalService> findAll() {
        String sql = "SELECT * FROM services ORDER BY name";
        List<AdditionalService> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapService(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения списка услуг", e);
        }
        return result;
    }

    public void addServiceToClient(UUID clientId, String serviceId) {
        String sql = "INSERT INTO client_services (client_id, service_id) VALUES (?, ?) ON CONFLICT (client_id, service_id) DO NOTHING";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, clientId);
            ps.setString(2, serviceId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления услуги клиенту", e);
        }
    }

    public List<String> findClientNamesByServiceId(String serviceId) {
        String sql = "SELECT CONCAT(c.surname, ' ', c.name, ' ', COALESCE(c.patronymic, '')) as client_name " +
                "FROM clients c " +
                "INNER JOIN client_services cs ON c.id = cs.client_id " +
                "WHERE cs.service_id = ? ORDER BY client_name";
        List<String> names = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, serviceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    names.add(rs.getString("client_name"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения имен клиентов услуги", e);
        }
        return names;
    }
}
