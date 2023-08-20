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

@WebServlet(urlPatterns = "/repairers/create")
public class CreateRepairerServlet extends HttpServlet {

    private final RepairerService repairerService =
            new RepairerServiceImpl(new RepairerJDBCRepository());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        PrintWriter writer = resp.getWriter();
        Map<String, String> body = objectMapper.readValue(req.getReader(), new TypeReference<>() {
        });
        String name = body.get("name");
        String jSon = objectMapper.writeValueAsString(repairerService.save(name));
        writer.println(jSon);
    }
}
