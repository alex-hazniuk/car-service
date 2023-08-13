package org.example.servlets.garage_slot_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.GarageSlot;
import org.example.repository.FileRepositories.CarServiceStoreHandler;
import org.example.repository.FileRepositories.GarageSlotFileRepository;
import org.example.service.GarageSlotService;
import org.example.service.GarageSlotServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/garageSlots/all")
public class AllGarageSlotsServlet extends HttpServlet {
    private final GarageSlotService garageSlotService = new GarageSlotServiceImpl(
            new GarageSlotFileRepository(
                    new CarServiceStoreHandler(Paths.get("src/main/resources/state.json"))));
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();

        List<GarageSlot> list = garageSlotService.getAll();
        for (GarageSlot garageSlot : list) {
            printWriter.println(objectMapper.writeValueAsString(garageSlot));
        }
    }


}
