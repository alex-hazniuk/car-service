package org.example.repository.JdbcRepositiries;

import org.example.DataSource;
import org.example.exception.DataProcessingException;
import org.example.model.Repairer;
import org.example.model.RepairerStatus;
import org.example.repository.RepairerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepairerJDBCRepository implements RepairerRepository {

    DataSource dataSource = new DataSource();

    private static final String SAVE =
            """
                    INSERT INTO repairer (name, status)
                    VALUES (?, ?)
                    """;
    private static final String FIND_BY_ID =
            """
                    SELECT * FROM repairer
                    WHERE id = ?
                    """;
    private static final String GET_ALL =
            """
                    SELECT * FROM repairer
                    """;
    private static final String DELETE =
            """
                    DELETE FROM repairer
                    WHERE id = ?
                    """;
    private static final String UPDATE =
            """
                    UPDATE repairer
                    SET name = ?, status = ?
                    WHERE id = ?
                    """;
    private static final String FIND_BY_NAME =
            """
                    SELECT * FROM repairer
                    WHERE name = ?
                    """;

    @Override
    public Repairer add(Repairer repairer) {
        try (Connection connection = dataSource.getConnection();
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
            throw new DataProcessingException("Can't save repairer " + repairer, e);
        }
    }

    @Override
    public Optional<Repairer> findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.ofNullable(getRepairer(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get repairer by id: " + id, e);
        }
    }

    @Override
    public List<Repairer> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return getRepairers(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all repairers ", e);
        }
    }

    @Override
    public boolean remove(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't remove repairer by id: " + id, e);
        }
    }

    @Override
    public Repairer update(Repairer repairer) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, repairer.getName());
            preparedStatement.setString(2, repairer.getStatus().toString());
            preparedStatement.setInt(3, repairer.getId());
            preparedStatement.executeUpdate();
            return repairer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update repairer " + repairer, e);
        }
    }

    @Override
    public Optional<Repairer> findByName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.ofNullable(getRepairer(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find repairer by name: " + name, e);
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
