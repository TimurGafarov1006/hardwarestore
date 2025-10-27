package ru.itis.hardwarestore.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.hardwarestore.repositories.repositoryImpl.*;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.*;
import ru.itis.hardwarestore.services.serviceImpl.*;
import ru.itis.hardwarestore.services.serviceInterfaces.*;
import ru.itis.hardwarestore.utils.PropertiesUtil;

import java.time.Duration;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = PropertiesUtil.getProperties();
        Duration sessionDuration = Duration.ofMinutes(Integer.parseInt(properties.getProperty("sessionDuration")));

        UserRepository userRepository = new UserRepositoryJdbc();
        SessionRepository sessionRepository = new SessionRepositoryJdbc();
        CategoryRepository categoryRepository = new CategoryRepositoryJdbc();
        ProductRepository productRepository = new ProductRepositoryJdbc();
        DiscountCardRepository discountCardRepository = new DiscountCardRepositoryJdbc();

        AuthService authService = new AuthServiceImpl(userRepository, sessionRepository);
        UserService userService = new UserServiceImpl(userRepository);
        CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
        ProductService productService = new ProductServiceImpl(productRepository);
        DiscountCardService discountCardService = new DiscountCardServiceImpl(discountCardRepository);

        sce.getServletContext().setAttribute("sessionRepository", sessionRepository);
        sce.getServletContext().setAttribute("sessionDuration", sessionDuration);
        sce.getServletContext().setAttribute("userService", userService);
        sce.getServletContext().setAttribute("authService", authService);
        sce.getServletContext().setAttribute("categoryService", categoryService);
        sce.getServletContext().setAttribute("productService", productService);
        sce.getServletContext().setAttribute("discountCardService", discountCardService);
    }
}
