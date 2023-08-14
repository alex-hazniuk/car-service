package org.example.servlets.order_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.management.actions.initServices.GenericInit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/orders/*")
public class GetOrderServlet extends HttpServlet {
    private final GenericInit genericInit = new GenericInit();

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();

        String id = req.getParameter("id");

        String jSon = mapper
                .writeValueAsString(genericInit.getOrderService().findById(Long.parseLong(id)));

        printWriter.println(jSon);
    }
}
