package org.example.servlets.order_servlets;

import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.Map;

@RequiredArgsConstructor
@Component
@WebServlet("/orders/assign-garage-slot/*")
public class AssignGarageSlotServlet extends HttpServlet {

    private final OrderService orderService;

    private final ObjectMapper mapper;

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        Map<String, Integer> body = mapper.readValue(req.getReader(),
                new TypeReference<>() {
                });
        long id = body.get("id");
        int garageId = body.get("garage_id");

        String jSon = mapper
                .writeValueAsString(orderService.assignGarageSlot(id, garageId));

        printWriter.println(jSon);
    }
}
