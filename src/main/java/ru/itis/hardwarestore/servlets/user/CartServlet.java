package ru.itis.hardwarestore.servlets.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.models.CartElement;
import ru.itis.hardwarestore.services.interfaces.CartElementService;
import ru.itis.hardwarestore.utils.JacksonUtils;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private CartElementService cartElementService;

    @Override
    public void init() throws ServletException {
        this.cartElementService = (CartElementService) getServletContext().getAttribute("cartElementService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = (String) req.getAttribute("userId");
        req.setAttribute("cart", cartElementService.getCartContains(userId));
        req.getRequestDispatcher("/WEB-INF/views/user/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = readJson(req);
        CartElement cartElement = JacksonUtils.jsonToCartElement(json);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        cartElementService.addOrUpdate(cartElement);

        boolean redirectAfter = node.has("redirectAfter") && node.get("redirectAfter").asBoolean();
        if (redirectAfter) {
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write("{\"redirect\": \"" + req.getContextPath() + "/cart\"}");
        } else {
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write("{\"success\": true}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = readJson(req);
        CartElement cartElement = JacksonUtils.jsonToCartElement(json);
        cartElementService.deleteProduct(cartElement);
    }

    private String readJson(HttpServletRequest req) {
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return jsonBuffer.toString();
    }
}
