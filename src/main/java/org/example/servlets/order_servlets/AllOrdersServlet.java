package org.example.servlets.order_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.management.actions.initServices.GenericInit;
import org.example.model.Order;
import org.example.service.SortType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/orders/all/*")
public class AllOrdersServlet extends HttpServlet {

    private final GenericInit genericInit = new GenericInit();

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();

        String sort = req.getParameter("sort");
        List<Order> list = genericInit.getOrderService().listOrders(SortType.valueOf(sort));

        for (Order order : list) {
            printWriter.println(mapper.writeValueAsString(order));
        }
    }
}
