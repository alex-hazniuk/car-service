package org.example.servlets.repairer_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.repository.FileRepositories.CarServiceStoreHandler;
import org.example.repository.FileRepositories.RepairerFileRepository;
import org.example.service.RepairerService;
import org.example.service.RepairerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

@WebServlet(urlPatterns = "/repairer")
public class GetRepairerServlet extends HttpServlet {
    private final RepairerService repairerService = new RepairerServiceImpl(
            new RepairerFileRepository(
                    new CarServiceStoreHandler(
                            Paths.get("src/main/resources/state.json"))));
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String id = req.getParameter("id");
        String jSon = objectMapper
                .writeValueAsString(repairerService.findById(Integer.parseInt(id)));
        writer.println(jSon);
    }
}
