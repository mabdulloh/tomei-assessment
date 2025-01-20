package org.tomei.assessment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tomei.assessment.client.ExternalProductService;
import org.tomei.assessment.dto.ProductOrderDto;
import org.tomei.assessment.dto.ProductResultDto;
import org.tomei.assessment.exception.NotFoundException;
import org.tomei.assessment.exception.OutOfStockException;
import org.tomei.assessment.exception.ValidationException;
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
    public ProductOrderDto placeOrder(ProductOrderDto productOrderDto) {
        final var productDto = findByProductId(productOrderDto.getProductId());
        validateProductOrderDto(productOrderDto);

        final var orderId = ProductUtil.generateOrderId();
        final var totalPrice = productDto.getPrice() * productOrderDto.getQuantity();
        productOrderDto.setOrderId(orderId);
        productOrderDto.setTitle(productDto.getTitle());
        productOrderDto.setTotalPrice(totalPrice);
        log.info("adding new order with details: {}", productOrderDto);
        productOrders.put(orderId, productOrderDto);
        return productOrderDto;
    }

    @Override
    public List<ProductOrderDto> fetchOrders() {
        return productOrders.values().stream().toList();
    }

    private void validateProductOrderDto(ProductOrderDto productOrderDto) {
        if (OUT_OF_STOCK.equals(ProductUtil.determineStatus(productOrderDto.getProductId()))) {
            log.error("Product with productId: {} is out of stock", productOrderDto.getProductId());
            throw new OutOfStockException("Product with productId: " + productOrderDto.getProductId() + " is out of stock");
        }

        if (productOrderDto.getQuantity() < 1) {
            log.error("Order quantity can not be lower than 1");
            throw new ValidationException("Order quantity can not be lower than 1");
        }
    }

}
