package org.example.servlets.order_servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@RequiredArgsConstructor
@Component
@WebServlet("/orders/assign_repairer")
public class AssignRepairerServlet extends HttpServlet {

    private final OrderService orderService;

    private final ObjectMapper mapper;

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        Map<String, Integer> body = mapper.readValue(req.getReader(),
                new TypeReference<>() {
                });
        long id = body.get("id");
        int repairerId = body.get("repairer_id");

        String jSon = mapper
                .writeValueAsString(orderService.assignRepairer(id, repairerId));

        printWriter.println(jSon);
    }
}
