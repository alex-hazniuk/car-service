package org.example.servlets.repairer_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.repository.jdbc_repository.JDBCRepairerRepositoryImpl;
import org.example.service.JDBCService.JDBCRepairerService;
import org.example.service.JDBCService.JDBCRepairerServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/repairer/delete")
public class DeleteRepairerServlet extends HttpServlet {
    /*private final RepairerService repairerService = new RepairerServiceImpl(
            new RepairerFileRepository(
                    new CarServiceStoreHandler(
                            Paths.get("src/main/resources/state.json"))));*/
    private final JDBCRepairerService repairerService =
            new JDBCRepairerServiceImpl(new JDBCRepairerRepositoryImpl());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        repairerService.remove(Integer.parseInt(id));
    }
}
