package ru.itis.hardwarestore.servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.models.Order;
import ru.itis.hardwarestore.models.Product;
import ru.itis.hardwarestore.services.serviceInterfaces.OrderService;

import java.io.IOException;
import java.util.Map;

@WebServlet("/cabinet/orders/*")
public class OrderServlet extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        this.orderService = (OrderService) getServletContext().getAttribute("orderService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String userId = (String) req.getAttribute("userId");

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
            req.setAttribute("orders", orderService.getOrders(userId));
            req.getRequestDispatcher("/WEB-INF/views/user/orders.jsp").forward(req, resp);
        } else {
            Integer orderId = Integer.parseInt(pathInfo.substring(1));

            Map<Order, Map<Product, Integer>> orderData = orderService.getOrder(orderId);
            Map.Entry<Order, Map<Product, Integer>> entry = orderData.entrySet().iterator().next();

            Order order = entry.getKey();
            Map<Product, Integer> products = entry.getValue();

            req.setAttribute("products", products);
            req.setAttribute("order", order);
            req.getRequestDispatcher("/WEB-INF/views/user/order-details.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = (String) req.getAttribute("userId");
        orderService.createOrder(userId);
        resp.sendRedirect(req.getContextPath() + "/cabinet/orders");
    }
}
