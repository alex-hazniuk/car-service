package org.example.servlets.repairer_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.repository.JPARepositories.RepairerJPARepository;
import org.example.service.RepairerService;
import org.example.service.RepairerServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/repairers/all")
public class GetAllRepairersServlet extends HttpServlet {

    private final RepairerService repairerService =
            new RepairerServiceImpl(new RepairerJPARepository());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        PrintWriter writer = resp.getWriter();
        String jSon = objectMapper
                .writeValueAsString(repairerService.getAll());
        writer.println(jSon);
    }
}
