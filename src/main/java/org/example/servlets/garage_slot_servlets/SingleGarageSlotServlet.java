package org.example.servlets.garage_slot_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.InvalidIdException;
import org.example.repository.JPARepositories.GarageSlotJPARepository;
import org.example.service.GarageSlotService;
import org.example.service.GarageSlotServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/garage-slots/only/*")
public class SingleGarageSlotServlet extends HttpServlet {
    private final GarageSlotService garageSlotService =
            new GarageSlotServiceImpl(new GarageSlotJPARepository());
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

