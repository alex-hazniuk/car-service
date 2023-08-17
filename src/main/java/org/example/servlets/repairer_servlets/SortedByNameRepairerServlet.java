package org.example.servlets.repairer_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.repository.JdbcRepositiries.RepairerJDBCRepository;
import org.example.service.JDBCService.JDBCRepairerServiceImpl;
import org.example.service.RepairerService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/repairers/sort/name")
public class SortedByNameRepairerServlet extends HttpServlet {

    private final RepairerService repairerService =
            new JDBCRepairerServiceImpl(new RepairerJDBCRepository());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        PrintWriter writer = resp.getWriter();
        String jSon = objectMapper
                .writeValueAsString(repairerService.sortedByName());
        writer.println(jSon);
    }
}
