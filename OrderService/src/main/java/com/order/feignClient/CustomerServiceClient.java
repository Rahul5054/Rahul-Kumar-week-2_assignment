package com.order.feignClient;

import com.order.dto.CustomerDto;
import com.order.entity.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CustomerService")
public interface CustomerServiceClient {

    @GetMapping("/customers/{customerId}")
    CustomerDto getCustomerById(@PathVariable Long customerId);
}
