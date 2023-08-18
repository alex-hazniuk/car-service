package org.example.repository.JdbcRepositiries;

import org.example.config.DataSource;
import org.example.exception.DataProcessingException;
import org.example.model.*;
import org.example.repository.OrderRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class OrderJDBCRepository implements OrderRepository {

    private final DataSource dataSource;
    private final GarageSlotJDBCRepository garageSlotJDBCRepository;

    private static final String INSERT_ORDER = "INSERT INTO ORDERS (price, created_at, completed_at, order_status, garage_slot_id) VALUES (?,?,?,?,?);";
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM ORDERS WHERE ORDERS.ID=?;";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM ORDERS;";
    private static final String SELECT_ALL_ORDERS_BY_PRICE = "SELECT * FROM ORDERS ORDER BY price;";
    private static final String SELECT_ALL_ORDERS_BY_CREATED_AT = "SELECT * FROM ORDERS ORDER BY created_at;";
    private static final String SELECT_ALL_ORDERS_BY_COMPLETED_AT = "SELECT * FROM ORDERS ORDER BY completed_at;";
    private static final String SELECT_ALL_ORDERS_BY_STATUS = "SELECT * FROM ORDERS ORDER BY STATUS;";
    private static final String SELECT_REPAIRER_BY_ORDER = "SELECT r.* FROM repairer r INNER JOIN order_repairer ro ON r.id = ro.repairer_id WHERE ro.order_id = ?;";
    private static final String UPDATE_ORDER = "UPDATE ORDERS SET price=?, created_at=?, completed_at=?,order_status=?,garage_slot_id=? where ORDERS.id =?";

    public OrderJDBCRepository() {
        this.dataSource = new DataSource();
        this.garageSlotJDBCRepository = new GarageSlotJDBCRepository();
    }


    @Override
    public Order save(Order order) {

        try (var conn = dataSource.getConnection();
             var prepStmt = conn.prepareStatement(INSERT_ORDER)) {
            prepStmt.setDouble(1, order.getPrice());
            prepStmt.setString(2, order.getCreatedAt().toString());
            prepStmt.setString(3, order.getCompletedAt().toString());
            prepStmt.setString(4, order.getStatus().toString());
            prepStmt.setInt(5, order.getGarageSlot().getId());
            prepStmt.executeUpdate();
            ResultSet resultSet = prepStmt.getGeneratedKeys();
            resultSet.next();
            order.setId((long) resultSet.getInt(1));
            return order;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't save order " + order, e);
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        Order order = null;
        try (var conn = dataSource.getConnection();
             var prepStmt = conn.prepareStatement(SELECT_ORDER_BY_ID)) {
            prepStmt.setInt(1, Math.toIntExact(id));
            ResultSet rs = prepStmt.executeQuery();
            if (rs.next()) {
                order = Order.builder()
                        .id((long) rs.getInt("id"))
                        .status(OrderStatus.valueOf(rs.getString("order_status")))
                        .createdAt(LocalDateTime.parse(rs.getString("created_at")))
                        .completedAt(LocalDateTime.parse(rs.getString("completed_at")))
                        .price(rs.getDouble("price"))
                        .garageSlot(garageSlotJDBCRepository.findById(rs.getInt("garage_slot_id")).orElse(new GarageSlot()))
                        .repairers(selectRepairersByOrderId(id))
                        .build();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find order by id " + id, e);
        }
        return Optional.ofNullable(order);
    }

    private Set<Repairer> selectRepairersByOrderId(Long id) {
        Set<Repairer> repairers = new HashSet<>();
        try (var conn = dataSource.getConnection();
             var prepStmt = conn.prepareStatement(SELECT_REPAIRER_BY_ORDER)) {
            prepStmt.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                Repairer rep = Repairer.builder()
                        .name(resultSet.getString("name"))
                        .id(resultSet.getInt("id"))
                        .status(RepairerStatus.valueOf(resultSet.getString("status")))
                        .build();
                repairers.add(rep);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return repairers;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL_ORDERS);
            while (rs.next()) {
                Order order = Order.builder()
                        .id((long) rs.getInt("id"))
                        .status(OrderStatus.valueOf(rs.getString("order_status")))
                        .createdAt(LocalDateTime.parse(rs.getString("created_at")))
                        .completedAt(LocalDateTime.parse(rs.getString("completed_at")))
                        .price(rs.getDouble("price"))
                        .garageSlot(garageSlotJDBCRepository.findById(rs.getInt("garage_slot_id")).orElse(new GarageSlot()))
                        .repairers(selectRepairersByOrderId((long) rs.getInt("id")))
                        .build();

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders", e);
        }
        return orders;
    }

    @Override
    public List<Order> findAllSortedByStatus() {
        List<Order> orders = new ArrayList<>();
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL_ORDERS_BY_STATUS);
            while (rs.next()) {
                Order order = Order.builder()
                        .id((long) rs.getInt("id"))
                        .status(OrderStatus.valueOf(rs.getString("order_status")))
                        .createdAt(LocalDateTime.parse(rs.getString("created_at")))
                        .completedAt(LocalDateTime.parse(rs.getString("completed_at")))
                        .price(rs.getDouble("price"))
                        .garageSlot(garageSlotJDBCRepository.findById(rs.getInt("garage_slot_id")).orElse(new GarageSlot()))
                        .repairers(selectRepairersByOrderId((long) rs.getInt("id")))
                        .build();

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders", e);
        }
        return orders;
    }

    @Override
    public List<Order> findAllSortedByPrice() {
        List<Order> orders = new ArrayList<>();
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL_ORDERS_BY_PRICE);
            while (rs.next()) {
                Order order = Order.builder()
                        .id((long) rs.getInt("id"))
                        .status(OrderStatus.valueOf(rs.getString("order_status")))
                        .createdAt(LocalDateTime.parse(rs.getString("created_at")))
                        .completedAt(LocalDateTime.parse(rs.getString("completed_at")))
                        .price(rs.getDouble("price"))
                        .garageSlot(garageSlotJDBCRepository.findById(rs.getInt("garage_slot_id")).orElse(new GarageSlot()))
                        .repairers(selectRepairersByOrderId((long) rs.getInt("id")))
                        .build();

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders", e);
        }
        return orders;
    }

    @Override
    public List<Order> findAllSortedByCreatedDate() {
        List<Order> orders = new ArrayList<>();
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL_ORDERS_BY_CREATED_AT);
            while (rs.next()) {
                Order order = Order.builder()
                        .id((long) rs.getInt("id"))
                        .status(OrderStatus.valueOf(rs.getString("order_status")))
                        .createdAt(LocalDateTime.parse(rs.getString("created_at")))
                        .completedAt(LocalDateTime.parse(rs.getString("completed_at")))
                        .price(rs.getDouble("price"))
                        .garageSlot(garageSlotJDBCRepository.findById(rs.getInt("garage_slot_id")).orElse(new GarageSlot()))
                        .repairers(selectRepairersByOrderId((long) rs.getInt("id")))
                        .build();

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders", e);
        }
        return orders;
    }

    @Override
    public List<Order> findAllSortedByCompletedDate() {
        List<Order> orders = new ArrayList<>();
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL_ORDERS_BY_COMPLETED_AT);
            while (rs.next()) {
                Order order = Order.builder()
                        .id((long) rs.getInt("id"))
                        .status(OrderStatus.valueOf(rs.getString("order_status")))
                        .createdAt(LocalDateTime.parse(rs.getString("created_at")))
                        .completedAt(LocalDateTime.parse(rs.getString("completed_at")))
                        .price(rs.getDouble("price"))
                        .garageSlot(garageSlotJDBCRepository.findById(rs.getInt("garage_slot_id")).orElse(new GarageSlot()))
                        .repairers(selectRepairersByOrderId((long) rs.getInt("id")))
                        .build();

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders", e);
        }
        return orders;
    }

    @Override
    public Order update(Long id, Order order) {
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(UPDATE_ORDER)) {
            stmt.setDouble(1, order.getPrice());
            stmt.setString(2, order.getCreatedAt().toString());
            stmt.setString(3, order.getCompletedAt().toString());
            stmt.setString(4, order.getStatus().name());
            stmt.setInt(5, order.getGarageSlot().getId());
            stmt.setInt(6, Math.toIntExact(id));
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            order.setId((long) rs.getInt("id"));
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update order " + order, e);
        }
    }
}
