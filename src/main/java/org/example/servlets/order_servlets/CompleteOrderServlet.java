package org.example.servlets.order_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebServlet("/orders/complete/*")
public class CompleteOrderServlet extends HttpServlet {

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
                .writeValueAsString(orderService.completeOrder(Long.parseLong(id)));

        printWriter.println(jSon);
    }
}
