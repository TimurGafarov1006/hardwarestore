package ru.itis.hardwarestore.servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.exceptions.UnauthorizedException;
import ru.itis.hardwarestore.services.serviceInterfaces.UserService;

import java.io.IOException;

@WebServlet("/cabinet")
public class CabinetServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = (String) req.getAttribute("userId");

        try {
            req.setAttribute("user", userService.getUser(userId));
        } catch (UnauthorizedException e) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }

        req.getRequestDispatcher("/WEB-INF/views/user/cabinet.jsp").forward(req, resp);
    }
}
