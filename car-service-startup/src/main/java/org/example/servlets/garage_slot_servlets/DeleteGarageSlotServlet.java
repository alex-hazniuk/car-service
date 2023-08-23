package org.example.servlets.garage_slot_servlets;

import org.example.service.GarageSlotService;
import lombok.RequiredArgsConstructor;
import org.example.exception.InvalidIdException;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Component
@WebServlet("/garage-slots/delete/*")
public class DeleteGarageSlotServlet extends HttpServlet {
    private final GarageSlotService garageSlotService;

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
