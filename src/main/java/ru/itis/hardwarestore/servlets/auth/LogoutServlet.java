package ru.itis.hardwarestore.servlets.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.SessionRepository;
import ru.itis.hardwarestore.utils.CookieUtils;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private SessionRepository sessionRepository;

    @Override
    public void init() throws ServletException {
        this.sessionRepository = (SessionRepository) getServletContext().getAttribute("sessionRepository");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = null;
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals("session_id")) {
                    sessionId = cookie.getValue();
                }
            }
        }

        if (sessionId != null) {
            sessionRepository.deleteSessionById(sessionId);
            CookieUtils.clearAuthCookie(resp);
        }

        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
