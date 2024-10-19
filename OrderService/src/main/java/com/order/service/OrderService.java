package com.order.service;

import com.order.entity.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    Order updateOrder(Order order);

    void cancelOrder(Long orderId);

    List<Order> getAllOrders();

    Order getOrderById(Long orderId);
}
