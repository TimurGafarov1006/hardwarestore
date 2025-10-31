package ru.itis.hardwarestore.repositories.interfaces;

import ru.itis.hardwarestore.models.DiscountCard;

import java.util.Optional;

public interface DiscountCardRepository {
    void save(DiscountCard discountCard);
    void update(DiscountCard discountCard);
    void delete(String id);
    Optional<DiscountCard> findByCardNo(String cardNo);
    Optional<DiscountCard> findByUserId(String userId);
}
