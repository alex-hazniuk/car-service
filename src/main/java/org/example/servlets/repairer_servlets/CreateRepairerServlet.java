package org.example.servlets.repairer_servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.repository.JdbcRepositiries.RepairerJDBCRepository;
import org.example.service.JDBCService.JDBCRepairerService;
import org.example.service.JDBCService.JDBCRepairerServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/repairers/create")
public class CreateRepairerServlet extends HttpServlet {

    private final JDBCRepairerService repairerService =
            new JDBCRepairerServiceImpl(new RepairerJDBCRepository());
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
