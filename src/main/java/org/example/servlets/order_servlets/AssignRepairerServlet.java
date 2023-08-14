package org.example.servlets.order_servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.management.actions.initServices.GenericInit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/orders/assign_repairer")
public class AssignRepairerServlet extends HttpServlet {

    private final GenericInit genericInit = new GenericInit();

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        Map<String, Integer> body = mapper.readValue(req.getReader(),
                new TypeReference<>() {
                });
        long id = body.get("id");
        int repairerId = body.get("repairer_id");

        String jSon = mapper
                .writeValueAsString(genericInit.getOrderService().assignRepairer(id, repairerId));

        printWriter.println(jSon);
    }
}
