package ru.itis.hardwarestore.servlets.catalog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.models.Category;
import ru.itis.hardwarestore.services.serviceInterfaces.CategoryService;
import ru.itis.hardwarestore.services.serviceInterfaces.ProductService;

import java.io.IOException;

@WebServlet("/catalog/*")
public class CategoryServlet extends HttpServlet {
    private CategoryService categoryService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        this.categoryService = (CategoryService) getServletContext().getAttribute("categoryService");
        this.productService = (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
            req.setAttribute("categories", categoryService.getChildrenCategories(null));
            req.getRequestDispatcher("/WEB-INF/views/catalog/categories.jsp").forward(req, resp);
        }

        String categorySlug = pathInfo.substring(1).split("/")[0];
        Category category = categoryService.getCategoryBySlug(categorySlug);

        if (category.getParentId() != null) {
            Category prevCategory = categoryService.getCategory(category.getParentId());
            req.setAttribute("prevCategory", prevCategory);
        }

        if (categoryService.getChildrenCategories(category.getId()).isEmpty()) {
            req.setAttribute("products", productService.getCategoryProducts(category.getId()));
            req.getRequestDispatcher("/WEB-INF/views/catalog/products.jsp").forward(req, resp);
        } else {
            req.setAttribute("categories", categoryService.getChildrenCategories(category.getId()));
            req.getRequestDispatcher("/WEB-INF/views/catalog/categories.jsp").forward(req, resp);
        }
    }
}
