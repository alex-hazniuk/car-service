package org.example.servlets.repairer_servlets;

import lombok.RequiredArgsConstructor;
import org.example.service.RepairerService;
import org.springframework.stereotype.Component;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
@WebServlet(urlPatterns = "/repairer/delete")
public class DeleteRepairerServlet extends HttpServlet {

    private final RepairerService repairerService;

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        repairerService.remove(Integer.parseInt(id));
    }
}
