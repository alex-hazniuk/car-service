package org.example.servlets.garage_slot_servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.InvalidIdException;
import org.example.repository.JdbcRepositiries.GarageSlotJDBCRepository;
import org.example.service.GarageSlotService;
import org.example.service.GarageSlotServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/garage-slots/delete/*")
public class DeleteGarageSlotServlet extends HttpServlet {
    private final GarageSlotService garageSlotService = new GarageSlotServiceImpl(new GarageSlotJDBCRepository());

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        var uri = req.getRequestURI();
        var id = uri.substring(uri.lastIndexOf('/') + 1);
        try {
            garageSlotService.remove(Integer.parseInt(id));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (InvalidIdException e) {
            printWriter.println("Not found this id");
        }
    }
}
