package org.example.servlets.garage_slot_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.service.GarageSlotService;
import lombok.RequiredArgsConstructor;
import org.example.model.GarageSlot;
import org.springframework.stereotype.Component;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
@RequiredArgsConstructor
@WebServlet("/garage-slots/all/*")
public class AllGarageSlotsServlet extends HttpServlet {
    private final GarageSlotService garageSlotService;
    private final ObjectMapper objectMapper;

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
