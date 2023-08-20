package org.example.servlets.order_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Order;
import org.example.repository.JdbcRepositiries.GarageSlotJDBCRepository;
import org.example.repository.JdbcRepositiries.OrderJDBCRepository;
import org.example.repository.JdbcRepositiries.RepairerJDBCRepository;
import org.example.repository.OrderRepository;
import org.example.service.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/orders/all/sort/completed")
public class AllOrdersOrderByCompletedDateServlet extends HttpServlet {

    private final GarageSlotService garageSlotService = new GarageSlotServiceImpl(new GarageSlotJDBCRepository());

    private final RepairerService repairerService = new RepairerServiceImpl(
            new RepairerJDBCRepository());

    private final OrderRepository orderRepository = new OrderJDBCRepository();

    private final OrderService orderService = new OrderServiceImpl(orderRepository,
            repairerService, garageSlotService);


    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();

        List<Order> list = orderService.getAllSortedByCompletedDate();

        for (Order order : list) {
            printWriter.println(mapper.writeValueAsString(order));
        }
    }
}
