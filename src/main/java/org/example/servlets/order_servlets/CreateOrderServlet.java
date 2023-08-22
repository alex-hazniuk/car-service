package org.example.servlets.order_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Order;
import org.example.repository.JPARepositories.GarageSlotJPARepository;
import org.example.repository.JPARepositories.OrderJPARepository;
import org.example.repository.JPARepositories.RepairerJPARepository;
import org.example.repository.OrderRepository;
import org.example.service.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@WebServlet("/orders/create/")
public class CreateOrderServlet extends HttpServlet {

    private final GarageSlotService garageSlotService = new GarageSlotServiceImpl(new GarageSlotJPARepository());

    private final RepairerService repairerService = new RepairerServiceImpl(
            new RepairerJPARepository());

    private final OrderRepository orderRepository = new OrderJPARepository();

    private final OrderService orderService = new OrderServiceImpl(orderRepository,
            repairerService, garageSlotService);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());

        String jSon = objectMapper.writeValueAsString(orderService.create(order));
        writer.println("Order was successfully created" + jSon);
    }
}

