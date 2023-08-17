package org.example.servlets.garage_slot_servlets;

import org.example.exception.InvalidIdException;
import org.example.repository.JdbcRepositiries.GarageSlotJDBCRepository;
import org.example.service.JDBCService.JDBCGarageSlotServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/garage-slots/delete/*")
public class DeleteGarageSlotServlet extends HttpServlet {
    private final JDBCGarageSlotServiceImpl garageSlotService = new JDBCGarageSlotServiceImpl(new GarageSlotJDBCRepository());

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
