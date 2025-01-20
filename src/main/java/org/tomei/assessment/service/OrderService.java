package org.tomei.assessment.service;

import org.tomei.assessment.dto.ProductOrderDto;

import java.util.List;

public interface OrderService {
    void placeOrder(ProductOrderDto productDto);
    List<ProductOrderDto> fetchOrders();
}
