package org.example.servlets.repairer_servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.service.RepairerService;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@RequiredArgsConstructor
@Component
@WebServlet(urlPatterns = "/repairer/change/status")
public class ChangeRepairerStatusServlet extends HttpServlet {
    private final RepairerService repairerService;
    private final ObjectMapper objectMapper;

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
