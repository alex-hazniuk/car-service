package org.example.servlets.order_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.management.actions.initServices.GenericInit;
import org.example.model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@WebServlet("/orders/create/")
public class CreateOrderServlet extends HttpServlet {

    private final GenericInit genericInit = new GenericInit();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());

        String jSon = objectMapper.writeValueAsString(genericInit.getOrderService().create(order));
        writer.println("Order was successfully created" + jSon);
    }
}

