package org.example.servlets.order_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Order;
import org.example.repository.JdbcRepositiries.GarageSlotJDBCRepository;
import org.example.repository.JdbcRepositiries.OrderJDBCRepository;
import org.example.repository.JdbcRepositiries.RepairerJDBCRepository;
import org.example.repository.OrderRepository;
import org.example.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/orders/all")
public class AllOrdersServlet extends HttpServlet {

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

        List<Order> list = orderService.getAll();

        for (Order order : list) {
            printWriter.println(mapper.writeValueAsString(order));
        }
    }
}
