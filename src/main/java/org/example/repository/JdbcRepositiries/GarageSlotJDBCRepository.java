package org.example.repository.JdbcRepositiries;

import lombok.RequiredArgsConstructor;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;
import org.example.repository.GarageSlotRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class GarageSlotJDBCRepository implements GarageSlotRepository {
    private final DataSource dataSource;

    @Override
    public void add(GarageSlot garageSlot) {

        var sql = "insert into garage_slot (status) values (?)";
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, garageSlot.getStatus().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GarageSlot> getAll() {
        var sql = "select * from garage_slot";
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            var result = statement.executeQuery(sql);
            return mapGarageSlots(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<GarageSlot> findById(int id) {
        var sql = "select * from garage_slot where id = " + id;
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            var result = statement.executeQuery(sql);
            var list = mapGarageSlots(result);
            if (!list.isEmpty()) {
                return Optional.of(list.get(0));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(GarageSlot garageSlot) {
        var sql = "delete from garage_slot where id =?";
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, garageSlot.getId());
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public GarageSlot update(GarageSlot garageSlot) {
        var sql = "update garage_slot set status = ? where id =" + garageSlot.getId();
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(sql)) {
            if (garageSlot.getStatus().name().equals("AVAILABLE")) {
                garageSlot.setStatus(GarageSlotStatus.UNAVAILABLE);
                statement.setString(1, garageSlot.getStatus().toString());
                statement.executeUpdate();
            } else {
                garageSlot.setStatus(GarageSlotStatus.AVAILABLE);
                statement.setString(1, garageSlot.getStatus().toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return garageSlot;
    }

    private List<GarageSlot> mapGarageSlots(ResultSet result) throws SQLException {
        var garageSlots = new ArrayList<GarageSlot>();
        while (result.next()) {
            garageSlots.add(GarageSlot.builder()
                    .id(result.getInt("id"))
                    .status(GarageSlotStatus.valueOf(result.getString("status")))
                    .build());
        }
        return garageSlots;
    }
}

