package org.example.repository.JdbcRepositiries;

import org.example.DataSource;
import org.example.exception.DataProcessingException;
import org.example.model.Order;
import org.example.repository.OrderRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderJDBCRepository implements OrderRepository {

    private final DataSource dataSource;

    private static final String INSERT_ORDER = "INSERT INTO ORDERS (price, created_at, completed_at, order_status, garage_slot_id) VALUES (?,?,?,?,?);";

    public OrderJDBCRepository() {
        this.dataSource = new DataSource();
    }


    @Override
    public Order save(Order order) {

        try(var conn = dataSource.getConnection(); var prepStmt = conn.prepareStatement(INSERT_ORDER)){
            prepStmt.setDouble(1,order.getPrice());
            prepStmt.setString(2,order.getCreatedAt().toString());
            prepStmt.setString(3,order.getCompletedAt().toString());
            prepStmt.setString(4,order.getStatus().toString());
            prepStmt.setInt(5,order.getGarageSlot().getId());
            prepStmt.executeUpdate();

        } catch (SQLException e){
            throw new DataProcessingException("Can't save order " + order,e);
        }
        return null;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order update(Long id, Order order) {
        return null;
    }
}
