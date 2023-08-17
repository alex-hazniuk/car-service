package org.example.servlets.garage_slot_servlets;

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

@WebServlet("/garage-slots/only/*")
public class SingleGarageSlotServlet extends HttpServlet {
    private final GarageSlotService garageSlotService =
            new JDBCGarageSlotServiceImpl(new GarageSlotJDBCRepository());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        var uri = req.getRequestURI();
        var id = uri.substring(uri.lastIndexOf('/') + 1);
        try {
            var garage = garageSlotService.findById(Integer.parseInt(id));
            printWriter.println(objectMapper.writeValueAsString(garage));
        } catch (InvalidIdException e) {
            printWriter.println("Not found this id");
        }
    }
}

