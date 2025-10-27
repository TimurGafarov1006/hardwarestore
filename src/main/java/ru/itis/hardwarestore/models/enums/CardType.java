package ru.itis.hardwarestore.models.enums;

import lombok.Getter;

@Getter
public enum CardType {
    SILVER(1, 5),
    GOLDEN(2, 7),
    PLATINUM(3, 10);

    private final int id;
    private final int discountPercent;

    CardType(int id, int discountPercent) {
        this.id = id;
        this.discountPercent = discountPercent;
    }

    public static CardType fromId(int id) {
        if (id == 1) return SILVER;
        if (id == 2) return GOLDEN;
        else return PLATINUM;
    }
}
