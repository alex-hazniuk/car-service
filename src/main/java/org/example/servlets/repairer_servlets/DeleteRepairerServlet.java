package org.example.servlets.repairer_servlets;

import org.example.repository.JdbcRepositiries.RepairerJDBCRepository;
import org.example.service.JDBCService.JDBCRepairerService;
import org.example.service.JDBCService.JDBCRepairerServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/repairer/delete")
public class DeleteRepairerServlet extends HttpServlet {

    private final JDBCRepairerService repairerService =
            new JDBCRepairerServiceImpl(new RepairerJDBCRepository());

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        repairerService.remove(Integer.parseInt(id));
    }
}
