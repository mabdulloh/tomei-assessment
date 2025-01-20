package org.tomei.assessment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tomei.assessment.constant.ApiConstant;
import org.tomei.assessment.dto.ProductOrderDto;
import org.tomei.assessment.service.OrderService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private static final String BASE_ROUTE = "/orders";


    @PostMapping(ApiConstant.API + ApiConstant.API_VERSION + BASE_ROUTE)
    public ResponseEntity<ProductOrderDto> placeOrder(@RequestBody ProductOrderDto productOrderDto) {
        log.info("Place order for: {}", productOrderDto);
        return ResponseEntity.ok(orderService.placeOrder(productOrderDto));
    }

    @GetMapping(ApiConstant.API + ApiConstant.API_VERSION + BASE_ROUTE)
    public ResponseEntity<List<ProductOrderDto>> fetchOrders() {
        return ResponseEntity.ok(orderService.fetchOrders());
    }
}
