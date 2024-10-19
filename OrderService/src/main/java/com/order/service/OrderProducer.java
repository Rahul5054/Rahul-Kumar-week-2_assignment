package com.order.service;

import com.order.config.RabbitMQConfig;
import com.order.dto.OrderDto;
import com.order.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;

    private final String orderQueue="order_queue";


    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendOrder(Order order){
        System.out.println("Sending order:- "+order);
        rabbitTemplate.convertAndSend(orderQueue,order);
    }

}
