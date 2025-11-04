package ru.itis.hardwarestore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.itis.hardwarestore.models.enums.CardStatus;
import ru.itis.hardwarestore.models.enums.CardType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class DiscountCard {
    private String id;
    private UUID userId;
    private String cardNo;
    private CardType cardType;
    private CardStatus cardStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
