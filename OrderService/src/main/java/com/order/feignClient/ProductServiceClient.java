package com.order.feignClient;

import com.order.dto.ProductDto;
import com.order.entity.Customer;
import com.order.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ProductService")
public interface ProductServiceClient {

    @GetMapping("/products/{productId}")
    ProductDto getProductById(@PathVariable("productId") Long productId);
}


