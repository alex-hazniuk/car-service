package org.example.servlets.garage_slot_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.GarageSlot;
import org.example.repository.JPARepositories.GarageSlotJPARepository;
import org.example.service.GarageSlotService;
import org.example.service.GarageSlotServiceImpl;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/garage-slots/all/*")
public class AllGarageSlotsServlet extends HttpServlet {
    private final GarageSlotService garageSlotService = new GarageSlotServiceImpl(
            new GarageSlotJPARepository());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        var uri = request.getRequestURI();
        var id = uri.substring(uri.lastIndexOf('/') + 1);
        if (id.equals("sorted")) {
            List<GarageSlot> sortedList = garageSlotService.sortedByStatus();
            for (GarageSlot garageSlot : sortedList) {
                writer.println(objectMapper.writeValueAsString(garageSlot));
            }
        } else {
            List<GarageSlot> list = garageSlotService.getAll();
            for (GarageSlot garageSlot : list) {
                writer.println(objectMapper.writeValueAsString(garageSlot));
            }
        }
    }
}
