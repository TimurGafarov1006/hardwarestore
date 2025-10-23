package ru.itis.hardwarestore.repositories.repositoryInterfaces;

import ru.itis.hardwarestore.models.Session;

import java.time.LocalDateTime;

public interface SessionRepository {
    void addSession(String userId, String sessionId, LocalDateTime expireAt);
    Session getSessionById(String sessionId);
    void deleteSessionById(String sessionId);
}
