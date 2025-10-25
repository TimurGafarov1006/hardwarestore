package ru.itis.hardwarestore.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.hardwarestore.models.Session;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.SessionRepository;

import java.io.IOException;
import java.time.LocalDateTime;

public class AuthenticationFilter extends HttpFilter {
    private SessionRepository sessionRepository;

    @Override
    public void init() throws ServletException {
        this.sessionRepository = (SessionRepository) getServletContext().getAttribute("sessionRepository");
    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String path = req.getRequestURI();
        String contextPath = req.getContextPath();
        String relativePath = path.substring(contextPath.length());

        boolean isPublic = isPublicResource(relativePath);
        boolean isAuthRestricted = isRestrictedForAuthenticated(relativePath);

        boolean isAuthenticated = checkAuthentication(req, res);

        if (isAuthenticated && isAuthRestricted) {
            res.sendRedirect(contextPath + "/profile");
            return;
        }

        if (!isAuthenticated && !isPublic) {
            res.sendRedirect(contextPath + "/login");
            return;
        }

        chain.doFilter(req, res);
    }


    private boolean checkAuthentication(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String sessionId = null;
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("session_id".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }

        if (sessionId != null) {
            try {
                Session session = sessionRepository.getSessionById(sessionId);
                if (session != null && session.getExpireAt().isAfter(LocalDateTime.now())) {
                    req.setAttribute("userId", session.getUserId());
                    return true;
                } else {
                    sessionRepository.deleteSessionById(sessionId);
                    clearAuthCookie(res);
                }
            } catch (IllegalArgumentException e) {
                clearAuthCookie(res);
            }
        }
        return false;
    }

    private void clearAuthCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("session_id", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private boolean isPublicResource(String path) {
        return path.equals("/") ||
                path.equals("/login") ||
                path.equals("/sign-up") ||
                path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/") ||
                path.startsWith("/resources/") ||
                path.endsWith(".css") ||
                path.endsWith(".js") ||
                path.endsWith(".png") ||
                path.endsWith(".jpg") ||
                path.endsWith(".jpeg") ||
                path.isEmpty();
    }

    private boolean isRestrictedForAuthenticated(String path) {
        return "/login".equals(path) || "/sign-up".equals(path);
    }
}
