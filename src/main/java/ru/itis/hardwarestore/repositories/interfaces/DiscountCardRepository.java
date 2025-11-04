package ru.itis.hardwarestore.repositories.interfaces;

import ru.itis.hardwarestore.models.DiscountCard;

import java.util.Optional;
import java.util.UUID;

public interface DiscountCardRepository {
    void save(DiscountCard discountCard);
    void update(DiscountCard discountCard);
    void delete(String id);
    Optional<DiscountCard> findByCardNo(String cardNo);
    Optional<DiscountCard> findByUserId(UUID userId);
}
