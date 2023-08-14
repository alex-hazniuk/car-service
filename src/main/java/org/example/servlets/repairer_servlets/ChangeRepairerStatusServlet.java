package org.example.servlets.repairer_servlets;

import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.Map;

@WebServlet(urlPatterns = "/repairer/change/status")
public class ChangeRepairerStatusServlet extends HttpServlet {
    private final RepairerService repairerService = new RepairerServiceImpl(
            new RepairerFileRepository(
                    new CarServiceStoreHandler(
                            Paths.get("src/main/resources/state.json"))));
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Map<String, Integer> body = objectMapper.readValue(req.getReader(), new TypeReference<>() {
        });
        int id = body.get("id");
        String jSon = objectMapper
                .writeValueAsString(repairerService.changeStatus(id));
        writer.println(jSon);
    }
}