package ru.itis.hardwarestore.services.serviceInterfaces;

import ru.itis.hardwarestore.models.DiscountCard;

public interface DiscountCardService {
    void openDiscountCard(String userId);
    void upgradeDiscountCard(String userId);
    DiscountCard getDiscountCard(String userId);
}
