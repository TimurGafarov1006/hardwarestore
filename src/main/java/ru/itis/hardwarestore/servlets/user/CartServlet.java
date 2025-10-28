package ru.itis.hardwarestore.servlets.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.models.CartElement;
import ru.itis.hardwarestore.services.serviceInterfaces.CartElementService;
import ru.itis.hardwarestore.utils.JacksonUtils;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private CartElementService cartElementService;
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        this.cartElementService = (CartElementService) getServletContext().getAttribute("cartElementService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/user/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        String json = jsonBuffer.toString();
        CartElement cartElement = JacksonUtils.jsonToCartElement(json);

        cartElementService.addOrUpdate(cartElement);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
