package org.example.servlets.order_servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.repository.JdbcRepositiries.GarageSlotJDBCRepository;
import org.example.repository.JdbcRepositiries.OrderJDBCRepository;
import org.example.repository.JdbcRepositiries.RepairerJDBCRepository;
import org.example.repository.OrderRepository;
import org.example.service.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/orders/assign-garage-slot/*")
public class AssignGarageSlotServlet extends HttpServlet {

    private final GarageSlotService garageSlotService = new GarageSlotServiceImpl(new GarageSlotJDBCRepository());

    private final RepairerService repairerService = new RepairerServiceImpl(
            new RepairerJDBCRepository());

    private final OrderRepository orderRepository = new OrderJDBCRepository();

    private final OrderService orderService = new OrderServiceImpl(orderRepository,
            repairerService, garageSlotService);


    private final ObjectMapper mapper = new ObjectMapper();

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
