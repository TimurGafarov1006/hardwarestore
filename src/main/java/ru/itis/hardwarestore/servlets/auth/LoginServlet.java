package ru.itis.hardwarestore.servlets.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.exceptions.user.LoginValidateException;
import ru.itis.hardwarestore.services.interfaces.AuthService;
import ru.itis.hardwarestore.utils.CookieUtils;

import java.io.IOException;
import java.time.Duration;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private AuthService authService;
    private Duration sessionDuration;

    @Override
    public void init() throws ServletException {
        this.authService = (AuthService) getServletContext().getAttribute("authService");
        this.sessionDuration = (Duration) getServletContext().getAttribute("sessionDuration");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        String sessionId = null;

        try {
            sessionId = authService.loginUser(login, password);
        } catch (LoginValidateException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
        }

        CookieUtils.createCookie(req, resp, sessionId, sessionDuration);
        resp.sendRedirect(req.getContextPath() + "/catalog");
    }
}
