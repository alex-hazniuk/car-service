package org.example.InMemoryRepositories;

import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.repository.InMemoryRepositories.OrderInMemoryRepository;
import org.example.repository.OrderRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OrderInMemoryRepositoryTest {

    private Map<Long, Order> orderMap = new HashMap<>();
    private OrderRepository orderInMemoryRepository = new OrderInMemoryRepository(orderMap);

    @Test
    void whenSavingSingleOrder_thenSingleOrderMustBeSaved() {
        Order order1 = new Order();
        order1.setId(1L);
        orderInMemoryRepository.save(order1);
        assertThat(orderInMemoryRepository).isNotNull();
        assertThat(orderMap).containsKey(order1.getId());
        assertThat(orderMap.get(1L)).isEqualTo(order1);
        assertThat(orderMap.size()).isEqualTo(1);
    }


    @Test
    void whenSearchingExisitngId_thenOrderMustBeFound() {
        Order order = new Order();
        order.setId(1L);
        orderInMemoryRepository.save(order);
        Optional<Order> foundedId = orderInMemoryRepository.findById(order.getId());
        assertThat(foundedId.get()).isEqualTo(order);
        assertThat(foundedId.get().getId()).isEqualTo(1L);
        assertThat(orderMap.size()).isEqualTo(1);

    }

    @Test
    void whenSearchingNonExisitngId_thenShouldReturnEmptyOptional() {
        Order order = new Order();
        order.setId(2L);
        Optional<Order> notFoundedId = orderInMemoryRepository.findById(order.getId());
        assertThat(notFoundedId).isEmpty();
        assertThat(orderMap.size()).isEqualTo(0);
    }

    @Test
    void whenFindingAllOrders_thenAllOrdersMustBeReturned() {
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        Order order4 = new Order();
        orderInMemoryRepository.save(order1);
        orderInMemoryRepository.save(order2);
        orderInMemoryRepository.save(order3);
        orderInMemoryRepository.save(order4);
        List<Order> orders = orderInMemoryRepository.findAll();
        assertThat(orders.size()).isEqualTo(4);
        assertThat(orders).containsExactly(order1, order2, order3, order4);
    }


    @Test
    void whenFindingAllSortedByStatus_thenShouldReturnListOfSortedOrdersByStatus() {
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        order1.setStatus(OrderStatus.COMPLETED);
        order2.setStatus(OrderStatus.COMPLETED);
        order3.setStatus(OrderStatus.CANCELED);
        orderInMemoryRepository.save(order1);
        orderInMemoryRepository.save(order2);
        orderInMemoryRepository.save(order3);
        List<Order> sortedOrders = orderInMemoryRepository.findAllSortedByStatus();
        assertThat(sortedOrders).containsExactly(order1, order2, order3);
    }

    @Test
    void whenFindingAllSortedByPrice_thenShouldReturnListOfSortedOrdersByPrice() {
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        order1.setPrice(1200.5);
        order2.setPrice(1200.1);
        order3.setPrice(1200.5);
        orderInMemoryRepository.save(order1);
        orderInMemoryRepository.save(order2);
        orderInMemoryRepository.save(order3);
        List<Order> sortedOrders = orderInMemoryRepository.findAllSortedByPrice();
        assertThat(sortedOrders).containsExactly(order2, order1, order3);
    }

    @Test
    void whenFindingAllSortedByCompletedDate_thenShouldReturnListOfSortedOrdersByCompletedDate() {
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        order1.setCompletedAt(LocalDateTime.parse("2023-08-21T10:30:00"));
        order2.setCompletedAt(LocalDateTime.parse("2023-08-22T10:30:00"));
        order3.setCompletedAt(LocalDateTime.parse("2023-08-23T12:15:00"));
        orderInMemoryRepository.save(order1);
        orderInMemoryRepository.save(order2);
        orderInMemoryRepository.save(order3);
        List<Order> sortedOrders = orderInMemoryRepository.findAllSortedByCompletedDate();
        assertThat(sortedOrders).containsExactly(order1, order2, order3);
    }

    @Test
    void whenUpdatingOrder_thenOrderMustBeUpdated() {
        Order oldOrder = new Order();
        oldOrder.setId(0L);
        orderInMemoryRepository.save(oldOrder);
        Order newOrder = new Order();
        newOrder.setId(1L);
        newOrder.setPrice(1200.56);
        orderInMemoryRepository.update(1L, newOrder);
        assertThat(orderMap).containsKey(1L);
        assertThat(orderMap).containsValue(newOrder);
    }

    @Test
    void getIdCounter_ReturnsIncrementedValue() {
        Map<Long, Order> orderMap = new HashMap<>();
        OrderInMemoryRepository orderInMemoryRepository = new OrderInMemoryRepository(orderMap);
        assertThat(orderInMemoryRepository.getIdCounter()).isEqualTo(1L);
        assertThat(orderInMemoryRepository.getIdCounter()).isEqualTo(2L);
        assertThat(orderInMemoryRepository.getIdCounter()).isEqualTo(3L);
    }
}