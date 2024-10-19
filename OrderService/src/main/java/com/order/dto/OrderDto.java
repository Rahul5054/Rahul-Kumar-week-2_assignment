package com.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long orderId;
    private Long productId;
    private Long customerId;
    private Date orderDate;
    private String status;
    private double totalAmount;


    private ProductDto productDto;

    private  CustomerDto customerDto;
}
