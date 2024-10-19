package com.product.listner;

import com.product.dto.OrderDto;
import org.springframework.stereotype.Component;

@Component public class OrderMessageListener {
    public void receiveMessage(OrderDto orderDto) {
        // Process the orderDto message here
        System.out.println("Received Order: " + orderDto);
    }
}
