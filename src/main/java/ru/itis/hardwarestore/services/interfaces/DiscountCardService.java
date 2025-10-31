package ru.itis.hardwarestore.services.interfaces;

import ru.itis.hardwarestore.models.DiscountCard;

public interface DiscountCardService {
    void openDiscountCard(String userId);
    void upgradeDiscountCard(String userId);
    DiscountCard getDiscountCard(String userId);
}
