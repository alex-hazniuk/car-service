package org.example.servlets.garage_slot_servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.InvalidIdException;
import org.example.repository.JdbcRepositiries.GarageSlotJDBCRepository;
import org.example.service.GarageSlotService;
import org.example.service.JDBCService.JDBCGarageSlotServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/garage-slots/changeStatus")
public class ChangeStatusGarageSlotServlet extends HttpServlet {
    private final GarageSlotService garageSlotService = new JDBCGarageSlotServiceImpl(
            new GarageSlotJDBCRepository());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        Map<String, Integer> body = objectMapper.readValue(req.getReader(),
                new TypeReference<>() {
                });
        var id = body.get("id");
        try {
            var jSon = objectMapper.writeValueAsString(garageSlotService.changeStatus(id));
            printWriter.println(jSon);
        } catch (InvalidIdException e) {
            printWriter.println("Not found this id");
        }
    }
}
