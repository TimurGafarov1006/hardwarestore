package ru.itis.hardwarestore.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.exceptions.user.OrderNotFoundException;
import ru.itis.hardwarestore.models.Order;
import ru.itis.hardwarestore.services.interfaces.OrderService;

import java.io.IOException;
import java.util.UUID;

public class OrderAuthorizationFilter extends HttpFilter {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        this.orderService = (OrderService) getServletContext().getAttribute("orderService");
    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        UUID userId = (UUID) req.getAttribute("userId");
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
            chain.doFilter(req, res);
            return;
        }

        try {
            Integer orderId = Integer.valueOf(pathInfo.substring(1));
            Order order = orderService.getOrder(orderId);

            if (!order.getUserId().equals(userId)) {
                throw new OrderNotFoundException(orderId);
            }

            chain.doFilter(req, res);

        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID");
        }
    }
}
