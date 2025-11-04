package ru.itis.hardwarestore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class Session {
    private String sessionId;
    private UUID userId;
    private LocalDateTime expireAt;
}
