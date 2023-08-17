package org.example.repository.JdbcRepositiries;

import org.example.DataSource;
import org.example.exception.DataBaseConnectionException;
import org.example.exception.DataProcessingException;
import org.example.model.GarageSlot;
import org.example.model.GarageSlotStatus;
import org.example.repository.GarageSlotRepository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class GarageSlotJDBCRepository implements GarageSlotRepository {

    DataSource dataSource = new DataSource();

    @Override
    public GarageSlot add(GarageSlot garageSlot) {

        var sql = "insert into garage_slot (status) values (?)";
        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, garageSlot.getStatus().toString());
                statement.executeUpdate();
                var resultSet = statement.getGeneratedKeys();
                resultSet.next();
                garageSlot.setId(resultSet.getInt(1));
                return garageSlot;
            } catch (SQLException e) {
                throw new DataProcessingException("Can't get all garage slots ", e);
            }
        } catch (SQLException e) {
            throw new DataBaseConnectionException("Error establishing database connection");
        }
    }

    @Override
    public List<GarageSlot> getAll() {
        var sql = "select * from garage_slot";
        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.createStatement()) {
                var result = statement.executeQuery(sql);
                return mapGarageSlots(result);
            } catch (SQLException e) {
                throw new DataProcessingException("Can't get all garage slots ", e);
            }
        } catch (SQLException e) {
            throw new DataBaseConnectionException("Error establishing database connection");
        }
    }

    @Override
    public Optional<GarageSlot> findById(int id) {
        var sql = "select * from garage_slot where id = " + id;
        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.createStatement()) {
                var result = statement.executeQuery(sql);
                var list = mapGarageSlots(result);
                if (!list.isEmpty()) {
                    return Optional.of(list.get(0));
                } else {
                    return Optional.empty();
                }
            } catch (SQLException e) {
                throw new DataProcessingException("Can't get garage slot by id: " + id, e);
            }
        } catch (SQLException e) {
            throw new DataBaseConnectionException("Error establishing database connection");
        }
    }


    @Override
    public boolean delete(GarageSlot garageSlot) {
        var sql = "delete from garage_slot where id =?";
        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement(sql)) {
                statement.setInt(1, garageSlot.getId());
                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new DataProcessingException("Can't delete garage slot: " + garageSlot.getId(), e);
            }
        } catch (SQLException e) {
            throw new DataBaseConnectionException("Error establishing database connection");
        }
    }

    @Override
    public GarageSlot update(GarageSlot garageSlot) {
        var sql = "update garage_slot set status = ? where id =" + garageSlot.getId();
        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement(sql)) {
                statement.setString(1, garageSlot.getStatus().toString());
                statement.executeUpdate();
                return garageSlot;
            } catch (SQLException e) {
                throw new DataProcessingException("Can't update garage slot " + garageSlot.getId(), e);
            }
        } catch (SQLException e) {
            throw new DataBaseConnectionException("Error establishing database connection");
        }
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

