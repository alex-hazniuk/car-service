package org.example.servlets.garage_slot_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.GarageSlot;
import org.example.repository.jdbc_repository.GarageSlotJDBCRepository;
import org.example.service.JDBCService.JDBCGarageSlotService;
import org.example.service.JDBCService.JDBCGarageSlotServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/garage-slots/all/*")
public class AllGarageSlotsServlet extends HttpServlet {
    private final JDBCGarageSlotService jdbcGarageSlotService =
            new JDBCGarageSlotServiceImpl(new GarageSlotJDBCRepository());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        var uri = request.getRequestURI();
        var id = uri.substring(uri.lastIndexOf('/') + 1);
        if (id.equals("sorted")) {
            List<GarageSlot> sortedList = jdbcGarageSlotService.sortedByStatus();
            for (GarageSlot garageSlot : sortedList) {
                printWriter.println(objectMapper.writeValueAsString(garageSlot));
            }
        } else {
            List<GarageSlot> list = jdbcGarageSlotService.getAll();
            for (GarageSlot garageSlot : list) {
                printWriter.println(objectMapper.writeValueAsString(garageSlot));
            }
        }
    }
}

