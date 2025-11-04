package ru.itis.hardwarestore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Order {
    private Integer id;
    private UUID userId;
    private double amountBeforeDiscount;
    private double discountAmount;
    private double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime closedAt;
}
