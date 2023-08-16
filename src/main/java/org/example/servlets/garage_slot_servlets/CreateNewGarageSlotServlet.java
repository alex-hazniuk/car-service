package org.example.servlets.garage_slot_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.repository.jdbc_repository.GarageSlotJDBCRepository;
import org.example.service.JDBCService.JDBCGarageSlotService;
import org.example.service.JDBCService.JDBCGarageSlotServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/garage-slots/create")
public class CreateNewGarageSlotServlet extends HttpServlet {

    private final JDBCGarageSlotService jdbcGarageSlotService =
            new JDBCGarageSlotServiceImpl(new GarageSlotJDBCRepository());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        var garage = jdbcGarageSlotService.save();
        var jSon = objectMapper.writeValueAsString(jdbcGarageSlotService.findById(garage.getId()));
        printWriter.println("Garage slot was successfully created:");
        printWriter.println(jSon);
        response.setStatus(HttpServletResponse.SC_OK);

    }
}
