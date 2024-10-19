package com.order.controller;

import com.order.dto.CustomerDto;
import com.order.dto.OrderDto;
import com.order.dto.ProductDto;
import com.order.entity.Order;
import com.order.feignClient.CustomerServiceClient;
import com.order.feignClient.ProductServiceClient;
import com.order.service.OrderProducer;
import com.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    private final ProductServiceClient productServiceClient;

    private final CustomerServiceClient customerServiceClient;

    private final OrderProducer orderProducer;

    public OrderController(OrderService orderService, ProductServiceClient productServiceClient, CustomerServiceClient customerServiceClient, OrderProducer orderProducer) {
        this.orderService = orderService;
        this.productServiceClient = productServiceClient;
        this.customerServiceClient = customerServiceClient;
        this.orderProducer = orderProducer;
    }


    @PostMapping
   public ResponseEntity<Order> createOrder(@RequestBody Order order){
        //Fetching Product and Customer details from their Services


        orderProducer.sendOrder(order);
        return ResponseEntity.ok(orderService.createOrder(order));

      // return orderService.createOrder(order);
   }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDto) {
        // Fetch the existing order
        Order existingOrder = orderService.getOrderById(orderId);
        if (existingOrder == null) {
            throw new RuntimeException("Order not found");
        }

        // Update the order details
//        existingOrder.setProductId(orderDto.getProductId());
//        existingOrder.setCustomerId(orderDto.getCustomerId());
        existingOrder.setOrderDate(orderDto.getOrderDate());
        existingOrder.setStatus(orderDto.getStatus());
//        existingOrder.setTotalAmount(orderDto.getTotalAmount());

        // Save the updated order
        Order updatedOrder = orderService.updateOrder(existingOrder);

        // Fetch updated Product and Customer details
        ProductDto productDto = productServiceClient.getProductById(updatedOrder.getProductId());
        CustomerDto customerDto = customerServiceClient.getCustomerById(updatedOrder.getCustomerId());

        // Create and return updated OrderDto
        OrderDto updatedOrderDto = new OrderDto();
        updatedOrderDto.setOrderId(updatedOrder.getOrderId());
        updatedOrderDto.setProductId(updatedOrder.getProductId());
        updatedOrderDto.setCustomerId(updatedOrder.getCustomerId());
        updatedOrderDto.setOrderDate(updatedOrder.getOrderDate());
        updatedOrderDto.setStatus(updatedOrder.getStatus());
        updatedOrderDto.setTotalAmount(updatedOrder.getTotalAmount());
        updatedOrderDto.setProductDto(productDto);
        updatedOrderDto.setCustomerDto(customerDto);

        return new ResponseEntity<>(updatedOrderDto, HttpStatus.OK);
    }

   @DeleteMapping("/{orderId}")
    public void cancelOrder(@PathVariable Long orderId){
       orderService.cancelOrder(orderId);
   }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDto> orderDtos = orders.stream().map(order -> {
            // Fetch Product and Customer details
            ProductDto productDto = productServiceClient.getProductById(order.getProductId());
            CustomerDto customerDto = customerServiceClient.getCustomerById(order.getCustomerId());

            // Create and return OrderDto
            OrderDto orderDto = new OrderDto();
            orderDto.setOrderId(order.getOrderId());
            orderDto.setProductId(order.getProductId());
            orderDto.setCustomerId(order.getCustomerId());
            orderDto.setOrderDate(order.getOrderDate());
            orderDto.setStatus(order.getStatus());
            orderDto.setTotalAmount(order.getTotalAmount());
            orderDto.setProductDto(productDto);
            orderDto.setCustomerDto(customerDto);

            return orderDto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(orderDtos);
    }


    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        // Fetch Product and Customer details
        ProductDto productDto = productServiceClient.getProductById(order.getProductId());
        CustomerDto customerDto = customerServiceClient.getCustomerById(order.getCustomerId());

        // Create and return OrderDto
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getOrderId());
        orderDto.setProductId(order.getProductId());
        orderDto.setCustomerId(order.getCustomerId());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setStatus(order.getStatus());
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setProductDto(productDto);
        orderDto.setCustomerDto(customerDto);

        return orderDto;
    }


}
