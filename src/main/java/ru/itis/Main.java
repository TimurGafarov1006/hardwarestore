package ru.itis;

import ru.itis.hardwarestore.models.DiscountCard;
import ru.itis.hardwarestore.models.enums.CardStatus;
import ru.itis.hardwarestore.models.enums.CardType;
import ru.itis.hardwarestore.repositories.impl.*;
import ru.itis.hardwarestore.repositories.interfaces.*;
import ru.itis.hardwarestore.services.impl.*;
import ru.itis.hardwarestore.services.interfaces.*;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
//        OrderRepository orderRepository = new OrderRepositoryJdbc();
//        OrderListRepository orderListRepository = new OrderListRepositoryJdbc();
//        CartElementRepository cartElementRepository = new CartElementRepositoryJdbc();
//        ProductRepository productRepository = new ProductRepositoryJdbc();
//        DiscountCardRepository discountCardRepository = new DiscountCardRepositoryJdbc();
//        DiscountCardService discountCardService = new DiscountCardServiceImpl(discountCardRepository);
//        ProductService productService = new ProductServiceImpl(productRepository);
//        CartElementService cartElementService = new CartElementServiceImpl(cartElementRepository, productService);
//
//        OrderService orderService = new OrderServiceImpl(orderRepository, orderListRepository, cartElementService, productService, discountCardService);
//        System.out.println(orderService.getOrder(1));

        DiscountCardRepository discountCardRepository = new DiscountCardRepositoryJdbc();
        DiscountCardService discountCardService = new DiscountCardServiceImpl(discountCardRepository);
        DiscountCard discountCard = discountCardService.getDiscountCard("317b5e16-5d60-402b-b499-474bc70502f6");
        discountCardService.upgradeDiscountCard("317b5e16-5d60-402b-b499-474bc70502f6");
        System.out.println(discountCardService.getDiscountCard("317b5e16-5d60-402b-b499-474bc70502f6"));
    }
}
