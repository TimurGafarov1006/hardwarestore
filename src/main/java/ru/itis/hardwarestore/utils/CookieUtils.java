package ru.itis.hardwarestore.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Duration;

public class CookieUtils {
    public static void createCookie(HttpServletRequest req, HttpServletResponse resp, String sessionId, Duration sessionDuration) {
        Cookie cookie = new Cookie("session_id", sessionId);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(sessionDuration.toMinutesPart() * 60);
        resp.addCookie(cookie);
    }

    public static void clearAuthCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("session_id", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
