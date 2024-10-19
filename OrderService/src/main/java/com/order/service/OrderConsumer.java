package com.order.service;

import com.order.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

   @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
   public void consumeMessage(String message){
        System.out.println("Recieved message from RabbitMQ:- "+message);


    }
}

