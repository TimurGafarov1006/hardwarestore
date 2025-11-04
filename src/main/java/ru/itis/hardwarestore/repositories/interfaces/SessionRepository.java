package ru.itis.hardwarestore.repositories.interfaces;

import ru.itis.hardwarestore.models.Session;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SessionRepository {
    void addSession(UUID userId, String sessionId, LocalDateTime expireAt);
    Session getSessionById(String sessionId);
    void deleteSessionById(String sessionId);
}
