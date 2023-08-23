package org.example.servlets.garage_slot_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.service.GarageSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Component
@WebServlet("/garage-slots/create")
public class CreateNewGarageSlotServlet extends HttpServlet {

    private final GarageSlotService garageSlotService;
    private final ObjectMapper objectMapper;

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
