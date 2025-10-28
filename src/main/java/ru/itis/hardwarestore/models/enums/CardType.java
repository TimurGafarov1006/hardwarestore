package ru.itis.hardwarestore.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardType {
    SILVER(1, "Серебрянная", 5),
    GOLDEN(2, "Золотая", 7),
    PLATINUM(3, "Платиновая", 10);

    private final int id;
    private final String name;
    private final int discountPercent;


    public static CardType fromId(int id) {
        if (id == 1) return SILVER;
        if (id == 2) return GOLDEN;
        else return PLATINUM;
    }
}
