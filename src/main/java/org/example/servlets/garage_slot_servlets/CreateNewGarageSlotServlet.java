package org.example.servlets.garage_slot_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.repository.JPARepositories.GarageSlotJPARepository;
import org.example.service.GarageSlotService;
import org.example.service.GarageSlotServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/garage-slots/create")
public class CreateNewGarageSlotServlet extends HttpServlet {

    private final GarageSlotService garageSlotService = new GarageSlotServiceImpl(new GarageSlotJPARepository());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        var garage = garageSlotService.save();
        var jSon = objectMapper.writeValueAsString(garage);
        printWriter.println("Garage slot was successfully created:");
        printWriter.println(jSon);
        response.setStatus(HttpServletResponse.SC_OK);

    }
}
