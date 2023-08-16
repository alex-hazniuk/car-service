package org.example.repository.jdbc_repository;

import org.example.model.Repairer;
import org.example.model.RepairerStatus;
import org.example.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCRepairerRepositoryImpl implements JDBCRepairerRepository {
    private static final String SAVE =
            """
                    INSERT INTO repairers (name, status)
                    VALUES (?, ?)
                    """;
    private static final String FIND_BY_ID =
            """
                    SELECT * FROM repairers
                    WHERE id = ?
                    """;
    private static final String GET_ALL =
            """
                    SELECT * FROM repairers
                    """;
    private static final String DELETE =
            """
                    DELETE FROM repairers
                    WHERE id = ?
                    """;
    private static final String UPDATE =
            """
                    UPDATE repairers
                    SET name = ?, status = ?
                    WHERE id = ?
                    """;
    private static final String FIND_BY_NAME =
            """
                    SELECT * FROM repairers
                    WHERE name = ?
                    """;
    //private final Connection connection = ConnectionUtil.getConnection();

    @Override
    public Repairer add(Repairer repairer) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, repairer.getName());
            preparedStatement.setString(2, repairer.getStatus().toString());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            repairer.setId(resultSet.getInt(1));
            return repairer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't save repairer " + repairer, e);
        }
    }

    @Override
    public Repairer findById(int id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return getRepairer(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get repairer by id: " + id, e);
        }
    }

    @Override
    public List<Repairer> getAll() {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return getRepairers(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all repairers ", e);
        }
    }

    @Override
    public boolean remove(int id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Repairer update(Repairer repairer) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, repairer.getName());
            preparedStatement.setString(2, repairer.getStatus().toString());
            preparedStatement.setInt(3, repairer.getId());
            preparedStatement.executeUpdate();
            return repairer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update repairer " + repairer, e);
        }
    }

    @Override
    public Repairer findByName(String name) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return getRepairer(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Can't find repairer by name: " + name, e);
        }
    }

    private List<Repairer> getRepairers(ResultSet resultSet) throws SQLException {
        ArrayList<Repairer> repairers = new ArrayList<>();
        while (resultSet.next()) {
            repairers.add(getRepairer(resultSet));
        }
        return repairers;
    }

    private Repairer getRepairer(ResultSet resultSet) throws SQLException {
        return Repairer.builder()
                .id(resultSet.getInt(1))
                .name(resultSet.getString(2))
                .status(RepairerStatus.valueOf(resultSet.getString(3)))
                .build();
    }
}
