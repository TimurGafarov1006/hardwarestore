package ru.itis.hardwarestore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
public class Session {
    private String sessionId;
    private String userId;
    private LocalDateTime expireAt;
}
