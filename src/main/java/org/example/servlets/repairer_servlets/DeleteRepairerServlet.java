package org.example.servlets.repairer_servlets;

import org.example.repository.JPARepositories.RepairerJPARepository;
import org.example.service.RepairerService;
import org.example.service.RepairerServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/repairer/delete")
public class DeleteRepairerServlet extends HttpServlet {

    private final RepairerService repairerService =
            new RepairerServiceImpl(new RepairerJPARepository());

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        repairerService.remove(Integer.parseInt(id));
    }
}
