package ru.itis.hardwarestore.models.enums;

import lombok.Getter;

@Getter
public enum CardType {
    SILVER(1, "Серебрянная", 5),
    GOLDEN(2, "Золотая", 7),
    PLATINUM(3, "Платиновая", 10);

    private final int id;
    private final String name;
    private final int discountPercent;

    CardType(int id, String name, int discountPercent) {
        this.id = id;
        this.name = name;
        this.discountPercent = discountPercent;
    }

    public static CardType fromId(int id) {
        if (id == 1) return SILVER;
        if (id == 2) return GOLDEN;
        else return PLATINUM;
    }
}
