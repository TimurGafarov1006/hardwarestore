package ru.itis.hardwarestore.services.impl;

import ru.itis.hardwarestore.exceptions.app.DiscountCardException;
import ru.itis.hardwarestore.exceptions.user.OrderNotFoundException;
import ru.itis.hardwarestore.exceptions.user.ProductNotFoundException;
import ru.itis.hardwarestore.models.*;
import ru.itis.hardwarestore.repositories.interfaces.OrderListRepository;
import ru.itis.hardwarestore.repositories.interfaces.OrderRepository;
import ru.itis.hardwarestore.services.interfaces.CartElementService;
import ru.itis.hardwarestore.services.interfaces.DiscountCardService;
import ru.itis.hardwarestore.services.interfaces.OrderService;
import ru.itis.hardwarestore.services.interfaces.ProductService;

import java.time.LocalDateTime;
import java.util.*;

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
        List<CartElement> cartElements = cartElementService.getUserCart(userId);
        if (cartElements.isEmpty()) {
            throw new IllegalStateException("Cannot create order: cart is empty");
        }
        // проверяем наличие товаров и считаем сумму
        double amountBeforeDiscount = 0;
        for (CartElement cartElement : cartElements) {
            Product product = productService.getProduct(cartElement.getProductId());
            int requestedQty = cartElement.getQuantity();
            if (product.getQuantity() < requestedQty) {
                throw new ProductNotFoundException(
                        "Not enough stock for product '%s'. Available: %d, requested: %d"
                                .formatted(product.getName(), product.getQuantity(), requestedQty)
                );
            }
            amountBeforeDiscount += requestedQty * product.getPricePerUnit();
        }

        Order order = new Order(
                null,
                userId,
                amountBeforeDiscount,
                0,
                0,
                LocalDateTime.now(),
                null,
                null
        );
        Integer orderId = orderRepository.save(order);
        if (orderId == null) {
            throw new RuntimeException("Failed to save order");
        }

        for (CartElement cartElement : cartElements) {
            OrderList orderList = new OrderList(orderId, cartElement.getProductId(), cartElement.getQuantity());
            orderListRepository.save(orderList);
        }

        double discountAmount = 0;
        try {
            DiscountCard discountCard = discountCardService.getDiscountCard(userId);
            discountAmount = amountBeforeDiscount * discountCard.getCardType().getDiscountPercent() / 100.0;
        } catch (DiscountCardException e) {

        }

        double totalAmount = amountBeforeDiscount - discountAmount;

        order.setId(orderId);
        order.setAmountBeforeDiscount(amountBeforeDiscount);
        order.setDiscountAmount(discountAmount);
        order.setTotalAmount(totalAmount);
        orderRepository.update(order);

        // уменьшаем количество товара на складе
        for (CartElement cartElement : cartElements) {
            Product product = productService.getProduct(cartElement.getProductId());
            int newQuantity = product.getQuantity() - cartElement.getQuantity();
            product.setQuantity(newQuantity);
            product.setUpdatedAt(LocalDateTime.now());
            productService.updateQuantity(product);
        }

        // очищаем корзину только после успешного обновления склада
        for (CartElement cartElement : cartElements) {
            cartElementService.deleteProduct(cartElement);
        }

        // т.к я делал только юзерский UI, решил агрейдить карту сразу не после завершения заказа
        List<Order> userOrders = orderRepository.findAllByUserId(userId);
        if (userOrders.size() == 5 || userOrders.size() == 20) {
            discountCardService.upgradeDiscountCard(userId);
        }
    }

    @Override
    public List<Order> getOrders(String userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Map<Order, Map<Product, Integer>> getOrderContent(Integer orderId) {
        Map<Order, Map<Product, Integer>> result = new HashMap<>();
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            Map<Product, Integer> products = new HashMap<>();
            for (OrderList orderList : orderListRepository.findAllByOrderId(orderId)) {
                Product product = productService.getProduct(orderList.getProductId());
                products.put(product, orderList.getQuantity());
            }
            result.put(order, products);
            return result;
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    @Override
    public Order getOrder(Integer orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }
}
