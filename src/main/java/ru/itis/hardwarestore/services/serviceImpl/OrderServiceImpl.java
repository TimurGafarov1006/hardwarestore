package ru.itis.hardwarestore.services.serviceImpl;

import ru.itis.hardwarestore.exceptions.DiscountCardException;
import ru.itis.hardwarestore.models.*;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.OrderListRepository;
import ru.itis.hardwarestore.repositories.repositoryInterfaces.OrderRepository;
import ru.itis.hardwarestore.services.serviceInterfaces.CartElementService;
import ru.itis.hardwarestore.services.serviceInterfaces.DiscountCardService;
import ru.itis.hardwarestore.services.serviceInterfaces.OrderService;
import ru.itis.hardwarestore.services.serviceInterfaces.ProductService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderListRepository orderListRepository;
    CartElementService cartElementService;
    ProductService productService;
    DiscountCardService discountCardService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderListRepository orderListRepository, CartElementService cartElementService, ProductService productService, DiscountCardService discountCardService) {
        this.orderRepository = orderRepository;
        this.orderListRepository = orderListRepository;
        this.cartElementService = cartElementService;
        this.productService = productService;
        this.discountCardService = discountCardService;
    }

    @Override
    public void createOrder(String userId) {
        Order order = new Order(
                null,
                userId,
                0,
                0,
                0,
                LocalDateTime.now(),
                null,
                null
        );
        Integer orderId = orderRepository.save(order);

        double amountBeforeDiscount = 0;
        double discountAmount = 0;
        double totalAmount = 0;

        List<OrderList> orderLists = new ArrayList<>();
        List<CartElement> cartElements = cartElementService.getUserCart(userId);
        for (CartElement cartElement: cartElements) {
            Product product = productService.getProduct(cartElement.getProductId());
            amountBeforeDiscount += cartElement.getQuantity() * product.getPricePerUnit();

            OrderList orderList = new OrderList(orderId, cartElement.getProductId(), cartElement.getQuantity());
            orderLists.add(orderList);
            orderListRepository.save(orderList);
        }

        try {
            DiscountCard discountCard = discountCardService.getDiscountCard(userId);
            discountAmount = amountBeforeDiscount * discountCard.getCardType().getDiscountPercent() / 100;
        } catch (DiscountCardException e) {
            discountAmount = 0;
        }

        totalAmount = amountBeforeDiscount - discountAmount;

        order.setAmountBeforeDiscount(amountBeforeDiscount);
        order.setDiscountAmount(discountAmount);
        order.setTotalAmount(totalAmount);
        order.setId(orderId);

        orderRepository.update(order);

        // удаляю в конце, чтобы исключить вариант когда произойдёт ошибка в оформлении заказа и корзина стерется
        for (CartElement cartElement : cartElements) {
            cartElementService.deleteProduct(cartElement);
        }
    }

    @Override
    public List<Order> getOrders(String userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
