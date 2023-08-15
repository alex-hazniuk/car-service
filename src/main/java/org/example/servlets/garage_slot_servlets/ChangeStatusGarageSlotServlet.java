package org.example.servlets.garage_slot_servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.InvalidIdException;
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
import java.util.Map;

@WebServlet("/garage-slots/changeStatus")
public class ChangeStatusGarageSlotServlet extends HttpServlet {
    private final GarageSlotService garageSlotService = new GarageSlotServiceImpl(
            new GarageSlotFileRepository(
                    new CarServiceStoreHandler(Paths.get("src/main/resources/state.json"))));
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
