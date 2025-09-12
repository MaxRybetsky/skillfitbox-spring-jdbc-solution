package ru.skillbox.skillfitbox.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.skillbox.skillfitbox.entity.Client;
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
@RequiredArgsConstructor
@Slf4j
public class LockerRepository {

    private final DataSource dataSource;

    private Locker mapLocker(ResultSet rs) throws SQLException {
        Locker locker = new Locker();
        locker.setId(rs.getObject("l_id", UUID.class));
        locker.setNumber(rs.getInt("l_number"));
        locker.setCreatedDatetime(rs.getObject("l_created_datetime", LocalDateTime.class));
        locker.setUpdatedDatetime(rs.getObject("l_updated_datetime", LocalDateTime.class));
        return locker;
    }

    private Client mapClient(ResultSet rs) throws SQLException {
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
        return client;
    }

    private Locker mapLockerWithClient(ResultSet rs) throws SQLException {
        Locker locker = mapLocker(rs);
        Client client = mapClient(rs);

        if (client.getId() != null) {
            locker.setClient(client);
        }
        return locker;
    }

    public Locker findById(UUID id) {
        String sql = """
                SELECT c.*,
                    l.id as l_id,
                    l.number as l_number,
                    l.created_datetime as l_created_datetime,
                    l.updated_datetime as l_updated_datetime
                FROM lockers l LEFT JOIN clients c ON l.client_id = c.id WHERE l.id = ?
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapLockerWithClient(rs);
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка поиска шкафчика по id", e);
            throw new RuntimeException("Ошибка поиска шкафчика по id", e);
        }
        return null;
    }

    public List<Locker> findAllWithClientInfo() {
        String sql = """
                SELECT c.*,
                    l.id as l_id,
                    l.number as l_number,
                    l.created_datetime as l_created_datetime,
                    l.updated_datetime as l_updated_datetime
                FROM lockers l LEFT JOIN clients c ON l.client_id = c.id ORDER BY l.number
                """;
        List<Locker> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapLockerWithClient(rs));
            }
        } catch (SQLException e) {
            log.error("Ошибка получения списка шкафчиков", e);
            throw new RuntimeException("Ошибка получения списка шкафчиков", e);
        }
        return result;
    }

    public void update(Locker locker) {
        String sql = "UPDATE lockers SET client_id = ?, updated_datetime = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            UUID clientId = Optional.ofNullable(locker.getClient())
                    .map(Client::getId)
                    .orElse(null);

            ps.setObject(1, clientId);
            ps.setObject(2, LocalDateTime.now());
            ps.setObject(3, locker.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Ошибка обновления шкафчика шкафчика", e);
            throw new RuntimeException("Ошибка обновления шкафчика шкафчика", e);
        }
    }
}
