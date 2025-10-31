package ru.itis.hardwarestore.repositories.interfaces;

import ru.itis.hardwarestore.models.OrderList;

import java.util.List;

public interface OrderListRepository {
    void save(OrderList orderList);
    List<OrderList> findAllByOrderId(int orderId);
}
