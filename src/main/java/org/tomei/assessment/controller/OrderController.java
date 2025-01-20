package org.tomei.assessment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tomei.assessment.dto.ProductOrderDto;
import org.tomei.assessment.service.OrderService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private static final String BASE_ROUTE = "/order";


    @PostMapping(BASE_ROUTE)
    public ProductOrderDto placeOrder(@RequestBody ProductOrderDto productOrderDto) {
        log.info("Place order for: {}", productOrderDto);
        return orderService.placeOrder(productOrderDto);
    }

    @GetMapping(BASE_ROUTE)
    public List<ProductOrderDto> fetchOrders() {
        return orderService.fetchOrders();
    }
}
