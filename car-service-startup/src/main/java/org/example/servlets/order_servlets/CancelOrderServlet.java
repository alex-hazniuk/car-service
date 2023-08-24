package org.example.servlets.order_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Component
@WebServlet("/orders/cansel/*")
public class CancelOrderServlet extends HttpServlet {

    private final OrderService orderService;

    private final ObjectMapper mapper;

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();

        String id = req.getParameter("id");

        String jSon = mapper
                .writeValueAsString(orderService.cancelOrder(Long.parseLong(id)));

        printWriter.println(jSon);
    }
}
