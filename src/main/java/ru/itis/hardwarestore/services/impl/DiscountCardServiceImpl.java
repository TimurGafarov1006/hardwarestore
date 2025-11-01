package ru.itis.hardwarestore.services.impl;

import ru.itis.hardwarestore.exceptions.app.DiscountCardException;
import ru.itis.hardwarestore.models.DiscountCard;
import ru.itis.hardwarestore.models.enums.CardStatus;
import ru.itis.hardwarestore.models.enums.CardType;
import ru.itis.hardwarestore.repositories.interfaces.DiscountCardRepository;
import ru.itis.hardwarestore.services.interfaces.DiscountCardService;
import ru.itis.hardwarestore.utils.DiscountCardUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class DiscountCardServiceImpl implements DiscountCardService {
    private final DiscountCardRepository discountCardRepository;

    public DiscountCardServiceImpl(DiscountCardRepository discountCardRepository) {
        this.discountCardRepository = discountCardRepository;
    }

    @Override
    public void openDiscountCard(String userId) {
        if (discountCardRepository.findByUserId(userId).isEmpty()) {
            DiscountCard discountCard = new DiscountCard(
                    UUID.randomUUID().toString(),
                    userId,
                    DiscountCardUtils.generateCardNumber(),
                    CardType.fromId(1),
                    CardStatus.valueOf("ACTIVE"),
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            discountCardRepository.save(discountCard);
        } else {
            throw new DiscountCardException("Discount card already exists");
        }
    }

    @Override
    public void upgradeDiscountCard(String userId) {
        Optional<DiscountCard> oldDiscountCard = discountCardRepository.findByUserId(userId);
        if (oldDiscountCard.isPresent()) {
            if (oldDiscountCard.get().getCardType().getId() < 3) {
                DiscountCard newDiscountCard = oldDiscountCard.get();
                newDiscountCard.setCardType(CardType.fromId(oldDiscountCard.get().getCardType().getId() + 1));
                discountCardRepository.update(newDiscountCard);
            } else {
                throw new DiscountCardException("Discount card has max level");
            }
        } else {
            throw new DiscountCardException("Discount card doesn't exists");
        }
    }

    @Override
    public DiscountCard getDiscountCard(String userId) {
        Optional<DiscountCard> oldDiscountCard = discountCardRepository.findByUserId(userId);
        if (oldDiscountCard.isPresent()) {
            return oldDiscountCard.get();
        } else {
            throw new DiscountCardException("Discount card doesn't exists");
        }
    }
}
