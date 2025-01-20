package org.tomei.assessment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tomei.assessment.client.ExternalProductService;
import org.tomei.assessment.dto.ProductOrderDto;
import org.tomei.assessment.dto.ProductResultDto;
import org.tomei.assessment.exception.NotFoundException;
import org.tomei.assessment.exception.OutOfStockException;
import org.tomei.assessment.service.OrderService;
import org.tomei.assessment.service.ProductService;
import org.tomei.assessment.util.ProductMapper;
import org.tomei.assessment.util.ProductUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService, OrderService {

    private final ExternalProductService externalProductService;
    private static final String OUT_OF_STOCK = "OUT_OF_STOCK";
    private final Map<String, ProductOrderDto> productOrders = new HashMap<>();

    @Override
    public List<ProductResultDto> fetchProducts() {
        return externalProductService.fetchProducts()
                .stream().map(ProductMapper::mapToProductResultDto)
                .toList();
    }

    @Override
    public ProductResultDto findByProductId(Integer productId) {
        log.info("Fetching product with productId: {}", productId);
        return externalProductService.findByProductId(productId)
                .map(ProductMapper::mapToProductResultDto)
                .orElseThrow(() -> new NotFoundException("Product not found with productId: " + productId));
    }

    @Override
    public void placeOrder(ProductOrderDto productOrderDto) {
        if (OUT_OF_STOCK.equals(ProductUtil.determineStatus(productOrderDto.getProductId()))) {
            log.error("Product with productId: {} is out of stock", productOrderDto.getProductId());
            throw new OutOfStockException("Product with productId: " + productOrderDto.getProductId() + " is out of stock");
        } else {
            final var orderId = ProductUtil.generateOrderId();
            final var totalPrice = productOrderDto.getPrice() * productOrderDto.getQuantity();
            productOrderDto.setOrderId(orderId);
            productOrderDto.setTotalPrice(totalPrice);
            productOrders.put(orderId, productOrderDto);
        }
    }

    @Override
    public List<ProductOrderDto> fetchOrders() {
        return productOrders.values().stream().toList();
    }

}
