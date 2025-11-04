package ru.itis.hardwarestore.services.interfaces;

import ru.itis.hardwarestore.models.DiscountCard;

import java.util.UUID;

public interface DiscountCardService {
    void openDiscountCard(UUID userId);
    void upgradeDiscountCard(UUID userId);
    DiscountCard getDiscountCard(UUID userId);
}
