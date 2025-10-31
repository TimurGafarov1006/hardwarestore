package ru.itis.hardwarestore.servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.exceptions.app.DiscountCardException;
import ru.itis.hardwarestore.models.DiscountCard;
import ru.itis.hardwarestore.services.interfaces.DiscountCardService;

import java.io.IOException;

@WebServlet("/cabinet/discount-card")
public class DiscountCardServlet extends HttpServlet {
    private DiscountCardService discountCardService;

    @Override
    public void init() throws ServletException {
        this.discountCardService = (DiscountCardService) getServletContext().getAttribute("discountCardService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = (String) req.getAttribute("userId");
        DiscountCard discountCard;

        try {
            discountCard = discountCardService.getDiscountCard(userId);
        } catch (DiscountCardException e) {
            discountCard = null;
        }

        req.setAttribute("discountCard", discountCard);
        req.getRequestDispatcher("/WEB-INF/views/user/discount-card.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = (String) req.getAttribute("userId");
        discountCardService.openDiscountCard(userId);

        resp.sendRedirect(req.getContextPath() + "/cabinet/discount-card");
    }
}
