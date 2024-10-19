package com.order.Implementation;

import com.order.Repository.OrderRepository;
import com.order.entity.Order;
import com.order.service.OrderProducer;
import com.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

   @Autowired
   private OrderRepository orderRepository;

   @Autowired
   private OrderProducer orderProducer;

    @Override
    public Order createOrder(Order order) {
        Order createOrder=orderRepository.save(order);


        return createOrder;
    }

    @Override
    public Order updateOrder(Order order) {

        Order updateOrder=this.orderRepository.save(order);
        return updateOrder;
    }


    @Override
    public void cancelOrder(Long orderId) {


        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
}
