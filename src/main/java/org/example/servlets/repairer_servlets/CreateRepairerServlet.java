package org.example.servlets.repairer_servlets;

import com.fasterxml.jackson.core.type.TypeReference;
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
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/repairers/create")
public class CreateRepairerServlet extends HttpServlet {
    /*private final RepairerService repairerService = new RepairerServiceImpl(
            new RepairerFileRepository(
                    new CarServiceStoreHandler(
                            Paths.get("src/main/resources/state.json"))));*/
    private final JDBCRepairerService repairerService =
            new JDBCRepairerServiceImpl(new JDBCRepairerRepositoryImpl());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Map<String, String> body = objectMapper.readValue(req.getReader(), new TypeReference<>() {
        });
        String name = body.get("name");
        String jSon = objectMapper.writeValueAsString(repairerService.save(name));
        writer.println(jSon);
    }
}
