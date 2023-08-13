package org.example.servlets.garage_slot_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.repository.FileRepositories.CarServiceStoreHandler;
import org.example.repository.FileRepositories.GarageSlotFileRepository;
import org.example.service.GarageSlotService;
import org.example.service.GarageSlotServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;


@WebServlet("/garageSlots/only/*")
public class SingleGarageSlotServlet extends HttpServlet {
    private final GarageSlotService garageSlotService = new GarageSlotServiceImpl(
            new GarageSlotFileRepository(
                    new CarServiceStoreHandler(Paths.get("src/main/resources/state.json"))));
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        var uri = req.getRequestURI();
        var id = uri.substring(uri.lastIndexOf('/') + 1);
        var garage = garageSlotService.findById(Integer.parseInt(id));
        printWriter.println(objectMapper.writeValueAsString(garage));
    }
}
