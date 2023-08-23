package org.example.servlets.order_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.example.model.Order;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@WebServlet("/orders/create/")
public class CreateOrderServlet extends HttpServlet {
    private final OrderService orderService;

    private final ObjectMapper objectMapper;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());

        String jSon = objectMapper.writeValueAsString(orderService.create(order));
        writer.println("Order was successfully created" + jSon);
    }
}

