package org.example.servlets.order_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.repository.JPARepositories.GarageSlotJPARepository;
import org.example.repository.JPARepositories.OrderJPARepository;
import org.example.repository.JPARepositories.RepairerJPARepository;
import org.example.repository.JdbcRepositiries.GarageSlotJDBCRepository;
import org.example.repository.JdbcRepositiries.OrderJDBCRepository;
import org.example.repository.JdbcRepositiries.RepairerJDBCRepository;
import org.example.repository.OrderRepository;
import org.example.service.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/orders/cansel/*")
public class CancelOrderServlet extends HttpServlet {

    private final GarageSlotService garageSlotService = new GarageSlotServiceImpl(new GarageSlotJPARepository());

    private final RepairerService repairerService = new RepairerServiceImpl(
            new RepairerJPARepository());

    private final OrderRepository orderRepository = new OrderJPARepository();

    private final OrderService orderService = new OrderServiceImpl(orderRepository,
            repairerService, garageSlotService);


    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();

        String id = req.getParameter("id");

        String jSon = mapper
                .writeValueAsString(orderService.cancelOrder(Long.parseLong(id)));

        printWriter.println(jSon);
    }
}
