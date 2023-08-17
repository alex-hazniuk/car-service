package org.example.servlets.garage_slot_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.GarageSlot;
import org.example.repository.JdbcRepositiries.GarageSlotJDBCRepository;
import org.example.service.JDBCService.JDBCGarageSlotServiceImpl;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/garage-slots/all/*")
public class AllGarageSlotsServlet extends HttpServlet {
    private final JDBCGarageSlotServiceImpl garageSlotService = new JDBCGarageSlotServiceImpl(
            new GarageSlotJDBCRepository());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        var uri = request.getRequestURI();
        var id = uri.substring(uri.lastIndexOf('/') + 1);
        if (id.equals("sorted")) {
            List<GarageSlot> sortedList = garageSlotService.sortedByStatus();
            for (GarageSlot garageSlot : sortedList) {
                printWriter.println(objectMapper.writeValueAsString(garageSlot));
            }
        } else {
            List<GarageSlot> list = garageSlotService.getAll();
            for (GarageSlot garageSlot : list) {
                printWriter.println(objectMapper.writeValueAsString(garageSlot));
            }
        }
    }
}
