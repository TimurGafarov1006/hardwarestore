package ru.itis.hardwarestore.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.hardwarestore.repositories.impl.*;
import ru.itis.hardwarestore.repositories.interfaces.*;
import ru.itis.hardwarestore.services.impl.*;
import ru.itis.hardwarestore.services.interfaces.*;
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
        CartElementRepository cartElementRepository = new CartElementRepositoryJdbc();
        OrderRepository orderRepository = new OrderRepositoryJdbc();
        OrderListRepository orderListRepository = new OrderListRepositoryJdbc();

        AuthService authService = new AuthServiceImpl(userRepository, sessionRepository);
        UserService userService = new UserServiceImpl(userRepository);
        CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
        ProductService productService = new ProductServiceImpl(productRepository);
        DiscountCardService discountCardService = new DiscountCardServiceImpl(discountCardRepository);
        CartElementService cartElementService = new CartElementServiceImpl(cartElementRepository, productService);
        OrderService orderService = new OrderServiceImpl(orderRepository, orderListRepository, cartElementService, productService, discountCardService);

        sce.getServletContext().setAttribute("sessionRepository", sessionRepository);
        sce.getServletContext().setAttribute("sessionDuration", sessionDuration);
        sce.getServletContext().setAttribute("userService", userService);
        sce.getServletContext().setAttribute("authService", authService);
        sce.getServletContext().setAttribute("categoryService", categoryService);
        sce.getServletContext().setAttribute("productService", productService);
        sce.getServletContext().setAttribute("discountCardService", discountCardService);
        sce.getServletContext().setAttribute("cartElementService", cartElementService);
        sce.getServletContext().setAttribute("orderService", orderService);
    }
}
