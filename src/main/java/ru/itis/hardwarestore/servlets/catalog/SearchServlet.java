package ru.itis.hardwarestore.servlets.catalog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.models.Product;
import ru.itis.hardwarestore.services.serviceInterfaces.ProductService;
import ru.itis.hardwarestore.utils.JacksonUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        this.productService = (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        List<Product> results = new ArrayList<>();

        if (query != null && !query.isEmpty()) {
            String lowerQuery = query.toLowerCase();
            for (Product product : productService.getProductsLikeName(lowerQuery)) {
                results.add(product);
            }
        }

        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(JacksonUtils.ProductsListToJson(results));
    }
}
