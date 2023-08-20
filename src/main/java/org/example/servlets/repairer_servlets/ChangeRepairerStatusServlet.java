package org.example.servlets.repairer_servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.repository.JdbcRepositiries.RepairerJDBCRepository;
import org.example.service.RepairerService;
import org.example.service.RepairerServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/repairer/change/status")
public class ChangeRepairerStatusServlet extends HttpServlet {
    private final RepairerService repairerService =
            new RepairerServiceImpl(new RepairerJDBCRepository());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        PrintWriter writer = resp.getWriter();
        Map<String, Integer> body = objectMapper.readValue(req.getReader(), new TypeReference<>() {
        });
        int id = body.get("id");
        String jSon = objectMapper
                .writeValueAsString(repairerService.changeStatus(id));
        writer.println(jSon);
    }
}
