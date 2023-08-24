package org.example.servlets.order_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.example.model.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RequiredArgsConstructor
@Component
@WebServlet("/orders/all/sort/completed")
public class AllOrdersOrderByCompletedDateServlet extends HttpServlet {

    private final OrderService orderService;

    private final ObjectMapper mapper;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();

        List<Order> list = orderService.getAllSortedByCompletedDate();

        for (Order order : list) {
            printWriter.println(mapper.writeValueAsString(order));
        }
    }
}
