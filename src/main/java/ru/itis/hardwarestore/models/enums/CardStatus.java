package ru.itis.hardwarestore.models.enums;

import lombok.Getter;

@Getter
public enum CardStatus {
    INACTIVE("INACTIVE"),
    ACTIVE("ACTIVE"),
    BLOCKED("BLOCKED");

    private String status;

    CardStatus(String status) {
        this.status = status;
    }
}
