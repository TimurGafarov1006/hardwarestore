package ru.itis.hardwarestore.servlets.catalog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.models.Category;
import ru.itis.hardwarestore.models.Product;
import ru.itis.hardwarestore.services.serviceInterfaces.CartElementService;
import ru.itis.hardwarestore.services.serviceInterfaces.CategoryService;
import ru.itis.hardwarestore.services.serviceInterfaces.ProductService;
import ru.itis.hardwarestore.services.serviceInterfaces.UserService;

import java.io.IOException;

@WebServlet("/products/*")
public class ProductServlet extends HttpServlet {
    private ProductService productService;
    private CategoryService categoryService;
    private UserService userService;
    private CartElementService cartElementService;

    @Override
    public void init() throws ServletException {
        this.productService = (ProductService) getServletContext().getAttribute("productService");
        this.categoryService = (CategoryService) getServletContext().getAttribute("categoryService");
        this.userService = (UserService) getServletContext().getAttribute("userService");
        this.cartElementService = (CartElementService) getServletContext().getAttribute("cartElementService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/catalog");
        }

        String productSlug = pathInfo.substring(1).split("/")[0];
        Product product = productService.getProductBySlug(productSlug);
        Category productCategory = categoryService.getCategory(product.getCategoryId());
        String userId = (String) req.getAttribute("userId");

        req.setAttribute("isProductInCart", cartElementService.isProductInCart(userId, product.getId()));
        req.setAttribute("user", userService.getUser(userId));
        req.setAttribute("product", product);
        req.setAttribute("productCategory", productCategory);
        req.getRequestDispatcher("/WEB-INF/views/catalog/specific-product.jsp").forward(req, resp);
    }
}
